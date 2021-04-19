package top.codecrab.common.shiro.session;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;
import top.codecrab.common.config.Constants;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * @author codecrab
 * @since 2021年04月19日 17:35
 */
public class CustomWebSessionManager extends DefaultWebSessionManager {
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String sessionId = WebUtils.toHttp(request).getHeader("Authorization");
        if (StringUtils.isEmpty(sessionId)) {
            return super.getSessionId(request, response);
        }
        sessionId = sessionId.replace(Constants.TOKEN_PREFIX, "");
        if (StringUtils.isEmpty(sessionId)) {
            return super.getSessionId(request, response);
        } else {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, "header");
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sessionId);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return sessionId;
        }
    }
}
