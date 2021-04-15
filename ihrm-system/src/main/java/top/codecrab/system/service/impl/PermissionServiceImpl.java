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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author codecrab
 * @since 2021年04月13日 15:00
 */
@Service
public class PermissionServiceImpl extends BaseService implements PermissionService {

    @Override
    public List<Permission> findAll(Integer type, String pid, Integer enVisible) {

        Specification<Permission> specification = ((root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (type != null) {
                CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("type"));
                if (Constants.PY_ZERO == type) {
                    in.value(1).value(2);
                } else {
                    in.value(type);
                }
            }

            //不为空添加查询条件，为空则查询所有
            if (StrUtil.equals(Constants.ZERO, pid)) {
                //表示查询私有功能列表
                list.add(criteriaBuilder.equal(root.get("parentId").as(String.class), pid));
            } else if (StrUtil.isNotBlank(pid)) {
                //表示查询指定的功能列表
                list.add(criteriaBuilder.equal(root.get("parentId").as(String.class), pid));
            }

            if (enVisible != null) {
                list.add(criteriaBuilder.equal(root.get("enVisible").as(Integer.class), enVisible));
            }

            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        });

        return permissionRepository.findAll(specification);
    }

    @Override
    public Map<String, Object> findById(String id) {
        Optional<Permission> optional = permissionRepository.findById(id);
        if (optional.isEmpty()) {
            return null;
        }
        Permission permission = optional.get();
        Map<String, Object> permissionMap = BeanUtil.beanToMap(permission);

        Object obj;
        Integer type = permission.getType();
        if (Constants.PY_MENU == type) {
            Optional<PermissionMenu> menuOptional = permissionMenuRepository.findById(id);
            if (menuOptional.isEmpty()) {
                //所对应的具体的子权限为空，则直接返回权限信息
                return permissionMap;
            }
            obj = menuOptional.get();
        } else if (Constants.PY_POINT == type) {
            Optional<PermissionPoint> pointOptional = permissionPointRepository.findById(id);
            if (pointOptional.isEmpty()) {
                return permissionMap;
            }
            obj = pointOptional.get();
        } else if (Constants.PY_API == type) {
            Optional<PermissionApi> apiOptional = permissionApiRepository.findById(id);
            if (apiOptional.isEmpty()) {
                return permissionMap;
            }
            obj = apiOptional.get();
        } else {
            return permissionMap;
        }

        Map<String, Object> objectMap = BeanUtil.beanToMap(obj);
        permissionMap.putAll(objectMap);
        return permissionMap;
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
        Permission permission = optional.get();

        List<Permission> permissions = permissionRepository.findAllByParentId(id);
        Set<String> set = new HashSet<>();
        set.add(id);
        while (permissions.size() > 0) {
            Set<String> ids = permissions.stream().map(Permission::getId).collect(Collectors.toSet());
            permissions = permissionRepository.findAllByParentIdIn(ids);
            set.addAll(ids);
        }
        permissionRepository.deleteAllByIdIn(set);

        int size = set.size();
        if (size == 1) {
            switch (permission.getType()) {
                case Constants.PY_MENU:
                    permissionMenuRepository.deleteAllByIdIn(set);
                    break;
                case Constants.PY_POINT:
                    permissionPointRepository.deleteAllByIdIn(set);
                    break;
                case Constants.PY_API:
                    permissionApiRepository.deleteAllByIdIn(set);
                    break;
                default:
                    throw new RuntimeException("权限实体类未知的type类型");
            }
        } else if (size > 1) {
            permissionMenuRepository.deleteAllByIdIn(set);
            permissionPointRepository.deleteAllByIdIn(set);
            permissionApiRepository.deleteAllByIdIn(set);
        }
    }
}
