package top.codecrab.system.base;

import org.springframework.beans.factory.annotation.Autowired;
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
    protected UserService userService;
    @Autowired
    protected RoleService roleService;
    @Autowired
    protected PermissionService permissionService;
}
