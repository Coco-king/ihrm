package top.codecrab.system.service;

import org.springframework.data.domain.Page;
import top.codecrab.common.entity.system.User;

import java.util.List;
import java.util.Map;

/**
 * @author codecrab
 * @since 2021年04月13日 9:11
 */
public interface UserService {
    /**
     * 查询所有用户
     *
     * @return 用户列表
     */
    List<User> findAll();

    /**
     * 分页条件查询所有用户
     *
     * @param companyId    公司id
     * @param departmentId 部门id
     * @param hasDept      是否分配部门  0：未分配 1：分配
     * @param page         当前页码
     * @param size         每页条数
     * @return 用户分页列表
     */
    Page<User> findAll(String companyId, String departmentId, String hasDept, int page, int size);

    /**
     * 根据id查询数据
     *
     * @param id 用户id
     * @return 用户
     */
    User findById(String id);

    /**
     * 保存用户
     *
     * @param user 用户
     * @return 保存后的用户
     */
    User save(User user);

    /**
     * 根据id修改用户
     *
     * @param user 用户
     * @return 是否成功
     */
    boolean update(User user);

    /**
     * 根据id删除用户
     *
     * @param id 用户id
     */
    void delete(String id);

    /**
     * 给指定用户绑定角色
     *
     * @param map keys：userId 用户id | ids：角色id列表
     * @return 是否成功
     */
    boolean assignRoles(Map<String, Object> map);

    /**
     * 修改指定用户状态
     *
     * @param id 用户id
     * @return 是否成功
     */
    boolean toggleStatus(String id);

    /**
     * 根据手机号查询用户
     *
     * @param mobile 手机号
     * @return 用户
     */
    User findByMobile(String mobile);
}
