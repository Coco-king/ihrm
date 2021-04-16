package top.codecrab.system.test;

import cn.hutool.core.date.DateUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import top.codecrab.common.config.Constants;
import top.codecrab.common.entity.system.User;

import java.util.Date;

/**
 * @author codecrab
 * @since 2021年04月15日 15:01
 */
public class JwtTest {

    @Test
    void testGetJwt() {
        User user = new User();
        user.setId("asd");
        user.setMobile("asd");
        user.setUsername("asd");
        user.setPassword("asd");
        user.setEnableState(0);
        user.setCreateTime(new Date());
        user.setCompanyId("asd");
        user.setCompanyName("asd");
        user.setDepartmentId("asd");
        user.setTimeOfEntry(new Date());
        user.setFormOfEmployment(0);
        user.setWorkNumber("asd");
        user.setFormOfManagement("asd");
        user.setWorkingCity("asd");
        user.setCorrectionTime(new Date());
        user.setInServiceStatus(0);
        user.setDepartmentName("asd");

        JwtBuilder jwtBuilder = Jwts.builder()
                .setId("666")
                .setSubject("小明")
                .setIssuedAt(new Date())
                .claim("user", user)
                .signWith(SignatureAlgorithm.HS256, "codecrab");
        String token = jwtBuilder.compact();
        // eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NjYiLCJzdWIiOiLlsI_mmI4iLCJpYXQiOjE2MTg0NzE2NzJ9.cO1Oe6bYwlBWqJ8xjDCS2MfClk1PM5Tt8oJB9nvXmuY
        System.out.println(token);
    }

    @Test
    void testParserToken() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiLnjovliJoiLCJjb21wYW55SWQiOiJ1c2VyLmdldENvbXBhbnlJZCgpIiwiY29tcGFueU5hbWUiOiJ1c2VyLmdldENvbXBhbnlOYW1lKCkiLCJleHAiOjE2MTg1NDQ4NjAsImlhdCI6MTYxODU0MTI2MCwianRpIjoiMjI4NDY5NTU2Mzg5NDc4NCJ9.9BlSte-EjYzeL6I1WIUEgUXLVcQvTYC6XmjR_jMxpJI";

        Claims claims = Jwts.parser().setSigningKey("ihrm@Login(Auth}*^31)&codecrab%").parseClaimsJws(token).getBody();

        System.out.println(claims.getId());
        System.out.println(claims.getSubject());
        System.out.println(claims.get("companyId"));
        System.out.println(claims.get("companyName"));
        System.out.println(DateUtil.format(claims.getIssuedAt(), Constants.DATE_PATTERN));
    }
}
