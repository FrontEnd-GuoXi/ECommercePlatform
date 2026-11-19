package org.guoxi.ecommerceplatform.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * JWT 工具类（基于 JJWT 0.12.6）
 * <p>
 * 密钥与过期时间从配置文件中读取：
 * jwt.secret（Base64 编码，解码后至少 256 位）
 * jwt.expiration（毫秒）
 */
@Component
public class JwtUtils {

    /** 用户名承载的 claim key */
    private static final String CLAIM_USERNAME = "username";

    private final SecretKey secretKey;

    /** Token 有效期（毫秒） */
    private final long expiration;

    public JwtUtils(@Value("${jwt.secret}") String secret,
                    @Value("${jwt.expiration}") long expiration) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.expiration = expiration;
    }

    /**
     * 生成 Token
     *
     * @param userId   用户ID，写入 subject
     * @param username 用户名，写入自定义 claim
     * @return 签名后的 JWT 字符串
     */
    public String generateToken(Long userId, String username) {
        Date now = new Date();
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim(CLAIM_USERNAME, username)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expiration))
                .signWith(secretKey)
                .compact();
    }

    /**
     * 解析并校验 Token（签名不合法或已过期都会抛 JwtException）
     *
     * @param token JWT 字符串
     * @return Token 中的载荷
     */
    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * 校验 Token 是否有效（签名正确且未过期）
     */
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * 从 Token 中取用户ID
     */
    public Long getUserId(String token) {
        return Long.valueOf(parseToken(token).getSubject());
    }

    /**
     * 从 Token 中取用户名
     */
    public String getUsername(String token) {
        return parseToken(token).get(CLAIM_USERNAME, String.class);
    }
}
