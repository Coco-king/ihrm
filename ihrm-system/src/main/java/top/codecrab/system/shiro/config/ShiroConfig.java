package top.codecrab.system.shiro.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.codecrab.common.shiro.realm.IhrmRealm;
import top.codecrab.common.shiro.session.CustomWebSessionManager;
import top.codecrab.system.shiro.realm.UserRealm;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author codecrab
 * @since 2021年04月19日 15:19
 */
@Configuration
public class ShiroConfig {

    @Bean
    public IhrmRealm ihrmRealm() {
        return new UserRealm();
    }

    /**
     * redis管理器
     * 默认的ip和端口都是 127.0.0.1:6379
     */
    @Bean
    public RedisManager redisManager() {
        return new RedisManager();
    }

    /**
     * 操作redis的对象
     */
    @Bean
    public RedisSessionDAO redisSessionDAO(RedisManager redisManager) {
        RedisSessionDAO dao = new RedisSessionDAO();
        dao.setRedisManager(redisManager);
        return dao;
    }

    /**
     * redis的缓存控制器 可有可无
     */
    @Bean
    public RedisCacheManager redisCacheManager(RedisManager redisManager) {
        RedisCacheManager cacheManager = new RedisCacheManager();
        cacheManager.setRedisManager(redisManager);
        return cacheManager;
    }

    /**
     * 创建安全管理器
     */
    @Bean
    public SecurityManager securityManager(
            IhrmRealm realm,
            SessionManager sessionManager,
            RedisCacheManager redisCacheManager
    ) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        // 标注使用DefaultWebSessionManager
        securityManager.setSessionManager(sessionManager);
        // 标注缓存处理器
        securityManager.setCacheManager(redisCacheManager);
        return securityManager;
    }

    /**
     * redis控制器
     */
    @Bean
    public SessionManager sessionManager(RedisSessionDAO redisSessionDAO) {
        CustomWebSessionManager sessionManager = new CustomWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO);
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        sessionManager.setSessionIdCookieEnabled(false);
        return sessionManager;
    }

    /**
     * 创建路径过滤器
     */
    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setLoginUrl("/authError?code=1");
        factoryBean.setSecurityManager(securityManager);
        factoryBean.setUnauthorizedUrl("/authError?code=2");

        Map<String, String> filterMap = new LinkedHashMap<>();
        //所有用户都可以访问
        filterMap.put("/authError", "anon");
        filterMap.put("/frame/login", "anon");
        filterMap.put("/frame/register/**", "anon");
        filterMap.put("/**", "authc");
        factoryBean.setFilterChainDefinitionMap(filterMap);
        return factoryBean;
    }

    /**
     * 开启注解支持
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
