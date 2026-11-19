package org.guoxi.ecommerceplatform.service.impl;

import lombok.RequiredArgsConstructor;
import org.guoxi.ecommerceplatform.common.Result;
import org.guoxi.ecommerceplatform.dto.LoginDTO;
import org.guoxi.ecommerceplatform.entity.User;
import org.guoxi.ecommerceplatform.enums.ResultCode;
import org.guoxi.ecommerceplatform.mapper.UserMapper;
import org.guoxi.ecommerceplatform.service.UserService;
import org.guoxi.ecommerceplatform.util.JwtUtils;
import org.guoxi.ecommerceplatform.vo.LoginVO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 用户业务实现
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public Result<LoginVO> login(LoginDTO loginDTO) {
        // 1. 按账号查询用户：DAO 内部按 username 或 phone 任一匹配
        User user = userMapper.selectByAccount(loginDTO.getAccount());

        // 2. 用户不存在：统一提示"用户不存在或密码错误"，避免暴露账号是否注册
        if (user == null) {
            return Result.fail(ResultCode.USER_NOT_FOUND);
        }

        // 3. 校验密码：明文与数据库中的 BCrypt 密文比对
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            return Result.fail(ResultCode.USER_NOT_FOUND);
        }

        // 4. 签发 Token 并组装响应（不下发 password）
        String token = jwtUtils.generateToken(user.getId(), user.getUsername());
        LoginVO loginVO = LoginVO.builder()
                .token(token)
                .userId(user.getId())
                .username(user.getUsername())
                .phone(user.getPhone())
                .build();
        return Result.success(loginVO);
    }
}
