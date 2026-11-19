package org.guoxi.ecommerceplatform.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 登录请求参数
 */
@Data
public class LoginDTO {

    /** 登录账号：支持用户名（username）或手机号（phone） */
    @NotBlank(message = "账号不能为空")
    private String account;

    /** 登录密码（明文，仅用于传输，校验后即丢弃） */
    @NotBlank(message = "密码不能为空")
    private String password;
}
