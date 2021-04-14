package top.codecrab.common.entity.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * EqualsAndHashCode注解：由于set集合的不能重复特性，导致jpa递归查询数据库。所以把Data注解自带的equals和hashCode排除掉users字段
 *
 * @author 20428
 */
@Data
@Entity
@Table(name = "pe_role")
@EqualsAndHashCode(exclude = "users")
public class Role implements Serializable {

    private static final long serialVersionUID = 594829320797158219L;

    @Id
    private String id;
    /**
     * 角色名
     */
    private String name;
    /**
     * 说明
     */
    private String description;
    /**
     * 企业id
     */
    private String companyId;

    /**
     * 角色与用户   多对多
     * mappedBy：不维护中间表，交给用户表去维护
     */
    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>(0);


    /**
     * 角色与模块  多对多
     */
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "pe_role_permission",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id", referencedColumnName = "id")})
    private Set<Permission> permissions = new HashSet<>(0);
}
