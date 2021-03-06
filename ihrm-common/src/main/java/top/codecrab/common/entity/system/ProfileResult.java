package top.codecrab.common.entity.system;

import lombok.Data;

import java.io.Serializable;
import java.util.*;

/**
 * @author codecrab
 * @since 2021年04月16日 9:57
 */
@Data
public class ProfileResult implements Serializable {

    private String id = "49908100275429716";

    private String mobile;

    private String companyId;

    private String companyName;

    private String username;

    private Map<String, Set<String>> roles = new HashMap<>();

    public ProfileResult(User user) {
        this.mobile = user.getMobile();
        this.companyId = user.getCompanyId();
        this.companyName = user.getCompanyName();
        this.username = user.getUsername();

        Set<String> menus = new HashSet<>();
        Set<String> points = new HashSet<>();
        Set<String> apis = new HashSet<>();
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                String code = permission.getCode();
                if (permission.getType() == 1) {
                    menus.add(code);
                } else if (permission.getType() == 2) {
                    points.add(code);
                } else {
                    apis.add(code);
                }
            }
        }
        this.roles.put("menus", menus);
        this.roles.put("points", points);
        this.roles.put("apis", apis);
    }

    public ProfileResult(User user, List<Permission> permissions) {
        this.mobile = user.getMobile();
        this.companyId = user.getCompanyId();
        this.companyName = user.getCompanyName();
        this.username = user.getUsername();

        Set<String> menus = new HashSet<>();
        Set<String> points = new HashSet<>();
        Set<String> apis = new HashSet<>();
        for (Permission permission : permissions) {
            String code = permission.getCode();
            if (permission.getType() == 1) {
                menus.add(code);
            } else if (permission.getType() == 2) {
                points.add(code);
            } else {
                apis.add(code);
            }
        }
        this.roles.put("menus", menus);
        this.roles.put("points", points);
        this.roles.put("apis", apis);
    }
}
