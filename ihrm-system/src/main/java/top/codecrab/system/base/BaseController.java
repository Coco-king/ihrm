package top.codecrab.system.base;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import top.codecrab.common.config.Constants;
import top.codecrab.common.config.SmsProperties;
import top.codecrab.common.entity.system.ProfileResult;
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

        Object obj = SecurityUtils.getSubject().getPrincipal();

        if (obj != null) {
            ProfileResult profileResult = (ProfileResult) obj;
            Constants.COMPANY_ID = profileResult.getCompanyId();
            Constants.COMPANY_NAME = profileResult.getCompanyName();
        }
    }
}
