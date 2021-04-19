package top.codecrab.common.interceptor;

import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import top.codecrab.common.config.Constants;
import top.codecrab.common.exception.CommonException;
import top.codecrab.common.response.ResultCode;
import top.codecrab.common.utils.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author codecrab
 * @since 2021年04月16日 17:13
 */
//@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    //@Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");
        if (StrUtil.isNotBlank(authorization) && authorization.startsWith(Constants.TOKEN_PREFIX)) {
            String token = authorization.replace(Constants.TOKEN_PREFIX, "");
            //解析token
            Claims claims = jwtUtils.getClaims(token);
            if (claims != null) {
                String apis = claims.get("apis", String.class);
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                RequestMapping annotation = handlerMethod.getMethodAnnotation(RequestMapping.class);
                if (apis != null && annotation != null && apis.contains(annotation.name())) {
                    //存入request
                    request.setAttribute("user_claims", claims);
                    return true;
                } else {
                    throw new CommonException(ResultCode.UNAUTHORISED);
                }
            }
        }
        throw new CommonException(ResultCode.UNAUTHENTICATED);
    }
}
