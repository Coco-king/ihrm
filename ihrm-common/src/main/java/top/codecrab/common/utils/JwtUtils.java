package top.codecrab.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @author codecrab
 * @since 2021年04月15日 15:41
 */
@Data
@Component
@ConfigurationProperties("jwt.config")
public class JwtUtils {

    /**
     * 私钥
     */
    private String privateKey;

    /**
     * 过期时间 单位：分钟
     */
    private long expireTime;

    public String generateToken(String id, String name, Map<String, Object> params) {
        // 根据当前时间获取过期时间
        long expire = (expireTime * 60000) + System.currentTimeMillis();
        // 生成token
        return Jwts.builder()
                .setId(id)
                .setSubject(name)
                .setClaims(params)
                .setIssuedAt(new Date())
                .setExpiration(new Date(expire))
                .signWith(SignatureAlgorithm.HS256, privateKey)
                .compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(privateKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public String getSubject(String token) {
        return Jwts.parser()
                .setSigningKey(privateKey)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public String getId(String token) {
        return Jwts.parser()
                .setSigningKey(privateKey)
                .parseClaimsJws(token)
                .getBody().getId();
    }

    public Object getProperty(String token, String propName) {
        return Jwts.parser()
                .setSigningKey(privateKey)
                .parseClaimsJws(token)
                .getBody().get(propName);
    }

    public <T> T getProperty(String token, String propName, Class<T> clazz) {
        return Jwts.parser()
                .setSigningKey(privateKey)
                .parseClaimsJws(token)
                .getBody().get(propName, clazz);
    }
}
