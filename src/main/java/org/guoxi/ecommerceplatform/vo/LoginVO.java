package org.guoxi.ecommerceplatform.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录响应视图
 * <p>
 * 只返回 token 与可公开的用户信息，password 等敏感字段一律不下发
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginVO {

    /** 登录凭证（JWT） */
    private String token;

    /** 用户ID */
    private Long userId;

    /** 用户名 */
    private String username;

    /** 手机号 */
    private String phone;
}
