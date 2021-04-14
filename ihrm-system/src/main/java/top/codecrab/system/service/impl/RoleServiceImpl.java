package top.codecrab.system.service.impl;

import cn.hutool.core.util.StrUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import top.codecrab.common.entity.system.Permission;
import top.codecrab.common.entity.system.Role;
import top.codecrab.system.base.BaseService;
import top.codecrab.system.service.RoleService;

import javax.transaction.Transactional;
import java.util.*;

/**
 * @author codecrab
 * @since 2021年04月13日 9:12
 */
@Service
public class RoleServiceImpl extends BaseService implements RoleService {

    @Override
    public Page<Role> findAll(String companyId, int page, int size) {
        Specification<Role> specification = ((root, query, criteriaBuilder) -> {
            if (StrUtil.isNotBlank(companyId)) {
                return criteriaBuilder.equal(root.get("companyId").as(String.class), companyId);
            }
            return null;
        });

        return roleRepository.findAll(specification, PageRequest.of(page - 1, size));
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(String id) {
        return roleRepository.findById(id).orElse(new Role());
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void save(Role role) {
        role.setId(idWorker.nextId().toString());
        roleRepository.save(role);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean update(Role role) {
        String roleId = role.getId();
        if (StrUtil.isBlankIfStr(roleId)) {
            return false;
        }
        Optional<Role> optional = roleRepository.findById(roleId);
        if (optional.isEmpty()) {
            return false;
        }

        Role temp = optional.get();
        temp.setName(role.getName());
        temp.setDescription(role.getDescription());
        roleRepository.saveAndFlush(temp);
        return true;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void delete(String id) {
        roleRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean assignPrem(Map<String, Object> map) {
        String roleId = (String) map.get("roleId");
        Object ids = map.get("ids");

        Optional<Role> optional = roleRepository.findById(roleId);
        if (optional.isEmpty()) {
            return false;
        }

        Role role = optional.get();
        role.setPermissions(new HashSet<>());

        if (ids instanceof ArrayList<?>) {
            // 遍历权限集合，添加权限
            for (Object o : (List<?>) ids) {
                Permission permission = new Permission();
                permission.setId((String) o);
                role.getPermissions().add(permission);
            }
            roleRepository.save(role);
            return true;
        }

        return false;
    }
}
