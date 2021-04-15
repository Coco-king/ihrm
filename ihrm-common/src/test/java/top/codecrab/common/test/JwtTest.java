package top.codecrab.common.test;
import java.util.HashSet;
import top.codecrab.common.entity.system.Role;

import cn.hutool.core.date.DatePattern;
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
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NjYiLCJzdWIiOiLlsI_mmI4iLCJpYXQiOjE2MTg0NzE5NjYsInVzZXIiOnsiaWQiOiJhc2QiLCJtb2JpbGUiOiJhc2QiLCJ1c2VybmFtZSI6ImFzZCIsInBhc3N3b3JkIjoiYXNkIiwiZW5hYmxlU3RhdGUiOjAsImNyZWF0ZVRpbWUiOjE2MTg0NzE5NjU4ODQsImNvbXBhbnlJZCI6ImFzZCIsImNvbXBhbnlOYW1lIjoiYXNkIiwiZGVwYXJ0bWVudElkIjoiYXNkIiwidGltZU9mRW50cnkiOjE2MTg0NzE5NjU4ODQsImZvcm1PZkVtcGxveW1lbnQiOjAsIndvcmtOdW1iZXIiOiJhc2QiLCJmb3JtT2ZNYW5hZ2VtZW50IjoiYXNkIiwid29ya2luZ0NpdHkiOiJhc2QiLCJjb3JyZWN0aW9uVGltZSI6MTYxODQ3MTk2NTg4NCwiaW5TZXJ2aWNlU3RhdHVzIjowLCJkZXBhcnRtZW50TmFtZSI6ImFzZCJ9fQ.mz0_pMbxPAICS7E2wCRTSEaSevNEj-PSoABH8YLgybo";

        Claims claims = Jwts.parser().setSigningKey("codecrab").parseClaimsJws(token).getBody();

        System.out.println(claims.getId());
        System.out.println(claims.getSubject());
        System.out.println(claims.get("user"));
        System.out.println(DateUtil.format(claims.getIssuedAt(), Constants.DATE_PATTERN));
    }
}
