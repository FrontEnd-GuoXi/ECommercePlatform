package org.guoxi.ecommerceplatform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 安全与认证相关配置
 */
@Configuration
public class SecurityConfig {

    /**
     * 密码加密器
     * <p>
     * 注册为 Spring Bean 后，Service 层可直接注入使用：
     * 加密用 encode(rawPassword)，比对用 matches(rawPassword, encodedPassword)
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
