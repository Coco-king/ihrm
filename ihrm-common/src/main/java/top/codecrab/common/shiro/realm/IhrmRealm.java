package top.codecrab.common.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import top.codecrab.common.entity.system.ProfileResult;

import java.util.Set;

/**
 * @author codecrab
 * @since 2021年04月19日 17:37
 */
public class IhrmRealm extends AuthorizingRealm {

    @Override
    public void setName(String name) {
        super.setName("ihrmRealm");
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        ProfileResult result = (ProfileResult) principals.getPrimaryPrincipal();
        Set<String> apis = result.getRoles().get("apis");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(apis);
        return info;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        return null;
    }
}
