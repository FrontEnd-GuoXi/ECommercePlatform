package org.guoxi.ecommerceplatform.service;

import org.guoxi.ecommerceplatform.common.Result;
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
     * @return 登录成功时 data 为 LoginVO（含 token），失败时返回错误提示
     */
    Result<LoginVO> login(LoginDTO loginDTO);
}
