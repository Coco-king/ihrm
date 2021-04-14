package top.codecrab.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import top.codecrab.common.entity.company.Department;

import java.util.List;

/**
 * @author codecrab
 * @since 2021年04月09日 10:35
 */
public interface DepartmentRepository extends JpaRepository<Department, String>, JpaSpecificationExecutor<Department> {

    /**
     * 根据公司id查询部门列表
     *
     * @param companyId 公司id
     * @return 部门列表
     */
    List<Department> findAllByCompanyId(String companyId);

    /**
     * 根据父id查询所有
     *
     * @param parentId 父id
     * @return 部门列表
     */
    List<Department> findAllByParentId(String parentId);

    /**
     * 根据父id查询所有
     *
     * @param ids 父ids
     * @return 部门列表
     */
    List<Department> findAllByParentIdIn(Iterable<String> ids);

    /**
     * 根据id删除
     *
     * @param ids ids
     * @return 删除的行数
     */
    int deleteAllByIdIn(Iterable<String> ids);
}
