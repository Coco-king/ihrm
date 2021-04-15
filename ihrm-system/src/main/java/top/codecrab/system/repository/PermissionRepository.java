package top.codecrab.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import top.codecrab.common.entity.system.Permission;

import java.util.List;

/**
 * @author codecrab
 * @since 2021年04月13日 14:57
 */
public interface PermissionRepository extends JpaRepository<Permission, String>, JpaSpecificationExecutor<Permission> {

    /**
     * 根据父id查询权限
     *
     * @param pid 父id
     * @return 权限列表
     */
    List<Permission> findAllByParentId(String pid);

    /**
     * 根据父id列表查询权限列表
     *
     * @param pids 父id列表
     * @return 权限列表
     */
    List<Permission> findAllByParentIdIn(Iterable<String> pids);

    /**
     * 根据id列表删除对应的记录
     *
     * @param ids id列表
     */
    void deleteAllByIdIn(Iterable<String> ids);
}
