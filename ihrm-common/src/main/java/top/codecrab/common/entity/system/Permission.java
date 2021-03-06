package top.codecrab.common.entity.system;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author 20428
 */
@Data
@Entity
@Table(name = "pe_permission")
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Permission implements Serializable {

    private static final long serialVersionUID = -4990810027542971546L;

    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限类型 1为菜单 2为功能 3为API
     */
    private Integer type;

    private String code;

    /**
     * 权限描述
     */
    private String description;

    @Column(name = "pid")
    @JsonAlias("pid")
    private String parentId;

    private Integer enVisible;

    public Permission(String name, Integer type, String code, String description) {
        this.name = name;
        this.type = type;
        this.code = code;
        this.description = description;
    }

}
