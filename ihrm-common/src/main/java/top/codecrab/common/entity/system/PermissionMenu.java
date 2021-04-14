package top.codecrab.common.entity.system;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 菜单权限实体类
 *
 * @author 20428
 */
@Data
@Entity
@Table(name = "pe_permission_menu")
public class PermissionMenu implements Serializable {
    private static final long serialVersionUID = -1002411490113957485L;

    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 展示图标
     */
    private String menuIcon;

    /**
     * 排序号
     */
    private String menuOrder;
}