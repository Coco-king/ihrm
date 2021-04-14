package top.codecrab.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import top.codecrab.common.config.Constants;
import top.codecrab.common.entity.system.Permission;
import top.codecrab.common.entity.system.PermissionApi;
import top.codecrab.common.entity.system.PermissionMenu;
import top.codecrab.common.entity.system.PermissionPoint;
import top.codecrab.system.base.BaseService;
import top.codecrab.system.service.PermissionService;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
                list.add(criteriaBuilder.equal(root.get("parentId").as(String.class), pid));
            }

            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        });

        return permissionRepository.findAll(specification);
    }

    @Override
    public Object findById(String id) {
        Optional<Permission> optional = permissionRepository.findById(id);
        if (optional.isEmpty()) {
            return new Permission();
        }
        Permission permission = optional.get();
        Integer type = permission.getType();
        switch (type) {
            case Constants.PY_MENU:
                Optional<PermissionMenu> menuOptional = permissionMenuRepository.findById(id);
                if (menuOptional.isEmpty()) {
                    return new PermissionMenu();
                }
                PermissionMenu menu = menuOptional.get();
                BeanUtil.copyProperties(permission, menu);
                return menu;
            case Constants.PY_POINT:
                Optional<PermissionPoint> pointOptional = permissionPointRepository.findById(id);
                if (pointOptional.isEmpty()) {
                    return new PermissionPoint();
                }
                PermissionPoint point = pointOptional.get();
                BeanUtil.copyProperties(permission, point);
                return point;
            case Constants.PY_API:
                Optional<PermissionApi> apiOptional = permissionApiRepository.findById(id);
                if (apiOptional.isEmpty()) {
                    return new PermissionApi();
                }
                PermissionApi api = apiOptional.get();
                BeanUtil.copyProperties(permission, api);
                return api;
            default:
                return permission;
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void save(Map<String, Object> map) {
        String id = idWorker.nextId().toString();
        Permission permission = BeanUtil.toBean(map, Permission.class);
        permission.setId(id);
        permission.setParentId((String) map.get("pid"));
        permission.setEnVisible(permission.getEnVisible() == null ? 0 : 1);

        savePermissionByType(map, id, permission.getType());
        permissionRepository.save(permission);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean update(Map<String, Object> map) {
        String permissionId = (String) map.get("id");
        if (StrUtil.isBlank(permissionId)) {
            return false;
        }

        Optional<Permission> optional = permissionRepository.findById(permissionId);
        if (optional.isEmpty()) {
            return false;
        }

        Permission permission = optional.get();
        Permission temp = BeanUtil.toBean(map, Permission.class);

        permission.setName(temp.getName());
        permission.setDescription(temp.getDescription());
        permission.setCode(temp.getCode());
        permission.setEnVisible(temp.getEnVisible());

        savePermissionByType(map, permissionId, temp.getType());
        permissionRepository.saveAndFlush(permission);
        return true;
    }

    private void savePermissionByType(Map<String, Object> map, String permissionId, Integer type) {
        switch (type) {
            case Constants.PY_MENU:
                PermissionMenu menu = BeanUtil.toBean(map, PermissionMenu.class);
                menu.setId(permissionId);
                permissionMenuRepository.save(menu);
                break;
            case Constants.PY_POINT:
                PermissionPoint point = BeanUtil.toBean(map, PermissionPoint.class);
                point.setId(permissionId);
                permissionPointRepository.save(point);
                break;
            case Constants.PY_API:
                PermissionApi api = BeanUtil.toBean(map, PermissionApi.class);
                api.setId(permissionId);
                permissionApiRepository.save(api);
                break;
            default:
                throw new RuntimeException("权限实体类未知的type类型");
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void delete(String id) {
        Optional<Permission> optional = permissionRepository.findById(id);
        if (optional.isEmpty()) {
            return;
        }
        Integer type = optional.get().getType();
        switch (type) {
            case Constants.PY_MENU:
                permissionMenuRepository.deleteById(id);
                break;
            case Constants.PY_POINT:
                permissionPointRepository.deleteById(id);
                break;
            case Constants.PY_API:
                permissionApiRepository.deleteById(id);
                break;
            default:
                throw new RuntimeException("权限实体类未知的type类型");
        }
        permissionRepository.deleteById(id);
    }
}
