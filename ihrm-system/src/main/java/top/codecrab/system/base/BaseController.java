package top.codecrab.system.base;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import top.codecrab.common.config.Constants;
import top.codecrab.common.config.SmsProperties;
import top.codecrab.common.utils.JwtUtils;
import top.codecrab.common.utils.SmsUtils;
import top.codecrab.system.service.PermissionService;
import top.codecrab.system.service.RoleService;
import top.codecrab.system.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author codecrab
 * @date 2021年04月09日 9:29
 */
public class BaseController {

    protected Claims claims;

    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;
    @Autowired
    protected JwtUtils jwtUtils;
    @Autowired
    protected SmsUtils smsUtils;
    @Autowired
    protected SmsProperties smsProperties;
    @Autowired
    protected UserService userService;
    @Autowired
    protected RoleService roleService;
    @Autowired
    protected PermissionService permissionService;

    @ModelAttribute
    public void setAttribute() {
        Object obj = request.getAttribute("user_claims");

        if (obj != null) {
            this.claims = (Claims) obj;
            Constants.COMPANY_ID = (String) claims.get("companyId");
            Constants.COMPANY_NAME = (String) claims.get("companyName");
        }
    }
}
