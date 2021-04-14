package top.codecrab.common.entity.company;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author codecrab
 * @since 2021-04-09
 */
@Data
@Entity
@Table(name = "co_department")
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    /**
     * 企业ID
     */
    private String companyId;

    /**
     * 父级部门ID
     */
    @Column(name = "pid")
    private String parentId;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 部门编码
     */
    private String code;

    /**
     * 部门负责人
     */
    private String manager;

    /**
     * 介绍
     */
    private String introduce;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 负责人ID
     */
    private String managerId;


}
