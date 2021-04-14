package top.codecrab.company.service;

import top.codecrab.common.entity.company.Department;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author codecrab
 * @since 2021-04-09
 */
public interface DepartmentService {

    /**
     * 保存部门
     *
     * @param department 部门实体类
     */
    void save(Department department);

    /**
     * 更新部门
     *
     * @param department 部门实体类
     * @return 是否成功
     */
    boolean update(Department department);

    /**
     * 更新部门
     *
     * @param id 部门id
     * @return 删除的条数
     */
    int delete(String id);

    /**
     * 根据id查询部门
     *
     * @param id 部门id
     * @return 部门bean
     */
    Department findById(String id);

    /**
     * 查询部门列表
     *
     * @return 部门集合
     */
    List<Department> findAll();

    /**
     * 根据企业id查询部门列表
     *
     * @param companyId 企业id
     * @return 部门集合
     */
    List<Department> findAll(String companyId);
}
