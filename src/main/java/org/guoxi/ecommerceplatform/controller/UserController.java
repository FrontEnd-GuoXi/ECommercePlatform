package org.guoxi.ecommerceplatform.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.guoxi.ecommerceplatform.common.Result;
import org.guoxi.ecommerceplatform.dto.LoginDTO;
import org.guoxi.ecommerceplatform.service.UserService;
import org.guoxi.ecommerceplatform.vo.LoginVO;
import org.springframework.web.bind.annotation.*;

/**
 * 用户相关接口
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 用户登录
     *
     * @param loginDTO 登录参数（账号支持用户名或手机号）
     * @return 登录成功返回 token 与用户信息
     */
    @PostMapping("/login")
    public Result<LoginVO> login(@Valid @RequestBody LoginDTO loginDTO) {
        return userService.login(loginDTO);
    }
}
