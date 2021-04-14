package top.codecrab.system.service;

import top.codecrab.common.entity.system.Permission;

import java.util.List;

/**
 * @author codecrab
 * @since 2021年04月13日 14:58
 */
public interface PermissionService {


    /**
     * 查询所有权限
     *
     * @param type 企业id
     * @param pid  父级d
     * @return 权限列表
     */
    List<Permission> findAll(int type, String pid);

    /**
     * 根据id查询权限
     *
     * @param id 权限id
     * @return 权限
     */
    Permission findById(String id);

    /**
     * 保存权限
     *
     * @param role 权限
     */
    void save(Permission role);

    /**
     * 根据id修改权限
     *
     * @param role 权限
     * @return 是否成功
     */
    boolean update(Permission role);

    /**
     * 根据id删除权限
     *
     * @param id 权限id
     */
    void delete(String id);
}
