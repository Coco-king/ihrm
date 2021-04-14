package top.codecrab.system.service;

import org.springframework.data.domain.Page;
import top.codecrab.common.entity.system.Role;

import java.util.List;
import java.util.Map;

/**
 * @author codecrab
 * @since 2021年04月13日 9:11
 */
public interface RoleService {

    /**
     * 查询所有角色
     *
     * @param companyId 企业id
     * @param page      当前页
     * @param size      每页条数
     * @return 角色列表
     */
    Page<Role> findAll(String companyId, int page, int size);

    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    List<Role> findAll();

    /**
     * 根据id查询角色
     *
     * @param id 角色id
     * @return 角色
     */
    Role findById(String id);

    /**
     * 保存角色
     *
     * @param role 角色
     */
    void save(Role role);

    /**
     * 根据id修改角色
     *
     * @param role 角色
     * @return 是否成功
     */
    boolean update(Role role);

    /**
     * 根据id删除角色
     *
     * @param id 角色id
     */
    void delete(String id);

    /**
     * 为角色添加权限
     *
     * @param map keys：roleId 角色id | ids：权限id列表
     * @return 是否成功
     */
    boolean assignPrem(Map<String, Object> map);
}
