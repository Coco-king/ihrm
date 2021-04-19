package top.codecrab.system.shiro.realm;

import org.apache.shiro.authc.*;
import org.springframework.beans.factory.annotation.Autowired;
import top.codecrab.common.config.Constants;
import top.codecrab.common.entity.system.Permission;
import top.codecrab.common.entity.system.ProfileResult;
import top.codecrab.common.entity.system.User;
import top.codecrab.common.shiro.realm.IhrmRealm;
import top.codecrab.system.service.PermissionService;
import top.codecrab.system.service.UserService;

import java.util.List;

/**
 * @author codecrab
 * @since 2021年04月19日 17:39
 */
public class UserRealm extends IhrmRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String mobile = token.getUsername();
        String password = new String(token.getPassword());
        //数据库查询
        User user = userService.findByMobile(mobile);
        if (user == null || !password.equals(user.getPassword())) {
            return null;
        }

        ProfileResult profileResult;
        //根据不同的level构建不同的数据
        if (Constants.SASS_USER.equals(user.getLevel())) {
            profileResult = new ProfileResult(user);
        } else {
            Integer enVisible = null;
            if (Constants.CO_ADMIN.equals(user.getLevel())) {
                enVisible = 1;
            }
            List<Permission> permissions = permissionService.findAll(null, null, enVisible);
            profileResult = new ProfileResult(user, permissions);
        }
        return new SimpleAuthenticationInfo(profileResult, password, this.getName());
    }
}
