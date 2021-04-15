package top.codecrab.system.service;

import top.codecrab.common.entity.system.Permission;

import java.util.List;
import java.util.Map;

/**
 * @author codecrab
 * @since 2021年04月13日 14:58
 */
public interface PermissionService {


    /**
     * 查询所有权限
     *
     * @param type      企业id
     * @param pid       父级d
     * @param enVisible 企业可见性
     * @return 权限列表
     */
    List<Permission> findAll(Integer type, String pid, Integer enVisible);

    /**
     * 根据id查询权限
     *
     * @param id 权限id
     * @return 权限
     */
    Object findById(String id);

    /**
     * 保存权限
     *
     * @param map 权限信息
     */
    void save(Map<String, Object> map);

    /**
     * 根据id修改权限
     *
     * @param map 权限
     * @return 是否成功
     */
    boolean update(Map<String, Object> map);

    /**
     * 根据id删除权限
     *
     * @param id 权限id
     */
    void delete(String id);
}
