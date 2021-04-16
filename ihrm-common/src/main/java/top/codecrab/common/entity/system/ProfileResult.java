package top.codecrab.common.entity.system;

import lombok.Data;

import java.util.*;

/**
 * @author codecrab
 * @since 2021年04月16日 9:57
 */
@Data
public class ProfileResult {

    private String mobile;

    private String company;

    private String username;

    private Map<String, Object> roles = new HashMap<>();

    public ProfileResult(User user) {
        this.mobile = user.getMobile();
        this.company = user.getCompanyName();
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
        this.company = user.getCompanyName();
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
