package top.codecrab.system.service.impl;

import cn.hutool.core.util.StrUtil;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import top.codecrab.common.config.Constants;
import top.codecrab.common.entity.system.Role;
import top.codecrab.common.entity.system.User;
import top.codecrab.system.base.BaseService;
import top.codecrab.system.service.UserService;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.*;

/**
 * @author codecrab
 * @since 2021年04月13日 9:12
 */
@Service
public class UserServiceImpl extends BaseService implements UserService {

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> findAll(String companyId, String departmentId, String hasDept, int page, int size) {

        Specification<User> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();

            // 不为空，拼接条件
            if (StrUtil.isNotBlank(companyId)) {
                list.add(criteriaBuilder.equal(root.get("companyId").as(String.class), companyId));
            }
            if (StrUtil.isNotBlank(departmentId)) {
                list.add(criteriaBuilder.equal(root.get("departmentId").as(String.class), departmentId));
            }

            if (StrUtil.isNotBlank(hasDept)) {
                if (Constants.ZERO.equals(hasDept)) {
                    // 表示部门未分配
                    list.add(criteriaBuilder.isNull(root.get("departmentId")));
                } else {
                    // 部门已分配
                    list.add(criteriaBuilder.isNotNull(root.get("departmentId")));
                }
            }

            //拼接条件
            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        };

        return userRepository.findAll(specification, PageRequest.of(page - 1, size));
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id).orElse(new User());
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public User save(User user) {
        user.setId(idWorker.nextId().toString());
        user.setEnableState(1);
        user.setLevel("user");
        user.setPassword("111111");
        user.setPassword(new Md5Hash(user.getPassword(), user.getMobile(), 3).toString());
        user.setTimeOfEntry(new Date());
        user.setCreateTime(new Date());
        return userRepository.save(user);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean update(User user) {
        String userId = user.getId();
        if (StrUtil.isBlankIfStr(userId)) {
            return false;
        }
        Optional<User> optional = userRepository.findById(userId);
        if (optional.isEmpty()) {
            return false;
        }

        User temp = optional.get();
        temp.setUsername(user.getUsername());
        temp.setMobile(user.getMobile());
        temp.setDepartmentName(user.getDepartmentName());
        temp.setDepartmentId(user.getDepartmentId());
        userRepository.saveAndFlush(temp);
        return true;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void delete(String id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean assignRoles(Map<String, Object> map) {
        String userId = (String) map.get("userId");
        Object ids = map.get("ids");

        Optional<User> optional = userRepository.findById(userId);
        if (optional.isEmpty()) {
            return false;
        }

        User user = optional.get();
        user.setRoles(new HashSet<>());

        if (ids instanceof ArrayList<?>) {
            // 遍历权限集合，添加权限
            for (Object o : (List<?>) ids) {
                Role role = new Role();
                role.setId((String) o);
                user.getRoles().add(role);
            }
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean toggleStatus(String id) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isEmpty()) {
            return false;
        }

        User user = optional.get();
        user.setEnableState(user.getEnableState().equals(0) ? 1 : 0);
        userRepository.saveAndFlush(user);
        return true;
    }

    @Override
    public User findByMobile(String mobile) {
        return userRepository.findByMobile(mobile);
    }
}
