package top.codecrab.system.service.impl;

import cn.hutool.core.util.StrUtil;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import top.codecrab.common.entity.system.Permission;
import top.codecrab.system.base.BaseService;
import top.codecrab.system.service.PermissionService;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author codecrab
 * @since 2021年04月13日 15:00
 */
@Service
public class PermissionServiceImpl extends BaseService implements PermissionService {

    @Override
    public List<Permission> findAll(int type, String pid) {

        Specification<Permission> specification = ((root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (type != 0) {
                list.add(criteriaBuilder.equal(root.get("type").as(Integer.class), type));
            }

            if (StrUtil.isNotBlank(pid)) {
                list.add(criteriaBuilder.equal(root.get("pid").as(String.class), pid));
            }

            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        });

        return permissionRepository.findAll(specification);
    }

    @Override
    public Permission findById(String id) {
        return permissionRepository.findById(id).orElse(new Permission());
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void save(Permission permission) {
        permission.setId(idWorker.nextId().toString());
        permissionRepository.save(permission);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean update(Permission permission) {
        String permissionId = permission.getId();
        if (StrUtil.isBlank(permissionId)) {
            return false;
        }

        Optional<Permission> optional = permissionRepository.findById(permissionId);
        if (optional.isEmpty()) {
            return false;
        }

        Permission temp = optional.get();
        temp.setName(permission.getName());
        temp.setDescription(permission.getDescription());

        permissionRepository.saveAndFlush(temp);
        return true;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void delete(String id) {
        permissionRepository.deleteById(id);
    }
}
