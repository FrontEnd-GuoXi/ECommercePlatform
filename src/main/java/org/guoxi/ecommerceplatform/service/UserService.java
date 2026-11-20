package org.guoxi.ecommerceplatform.service;

import org.guoxi.ecommerceplatform.dto.LoginDTO;
import org.guoxi.ecommerceplatform.vo.LoginVO;

/**
 * 用户业务接口
 */
public interface UserService {

    /**
     * 用户登录
     *
     * @param loginDTO 登录参数（账号支持用户名或手机号）
     * @return 登录成功时返回 token 与用户信息
     * @throws org.guoxi.ecommerceplatform.common.BusinessException 账号不存在、密码错误或账号被禁用
     */
    LoginVO login(LoginDTO loginDTO);
}
