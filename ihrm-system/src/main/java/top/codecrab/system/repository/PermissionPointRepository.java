package top.codecrab.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import top.codecrab.common.entity.system.PermissionPoint;

/**
 * @author codecrab
 * @since 2021年04月13日 14:57
 */
public interface PermissionPointRepository extends JpaRepository<PermissionPoint, String>, JpaSpecificationExecutor<PermissionPoint> {
}