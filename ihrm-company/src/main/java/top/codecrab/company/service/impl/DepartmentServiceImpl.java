package top.codecrab.company.service.impl;

import org.springframework.stereotype.Service;
import top.codecrab.common.entity.company.Department;
import top.codecrab.company.base.BaseService;
import top.codecrab.company.service.DepartmentService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author codecrab
 * @since 2021-04-09
 */
@Service
public class DepartmentServiceImpl extends BaseService implements DepartmentService {

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void save(Department department) {
        department.setId(idWorker.nextId().toString());
        department.setCreateTime(LocalDateTime.now());

        departmentRepository.save(department);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean update(Department department) {
        Optional<Department> optional = departmentRepository.findById(department.getId());
        if (!optional.isPresent()) {
            return false;
        }
        Department temp = optional.get();

        temp.setId(department.getId());
        temp.setName(department.getName());
        temp.setIntroduce(department.getIntroduce());
        departmentRepository.save(temp);
        return true;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public int delete(String id) {
        if (NULL.equals(id)) {
            long count = departmentRepository.count();
            departmentRepository.deleteAll();
            return (int) count;
        }
        List<Department> departments = departmentRepository.findAllByParentId(id);
        Set<String> set = new HashSet<>();
        set.add(id);
        while (departments.size() > 0) {
            Set<String> ids = departments.stream().map(Department::getId).collect(Collectors.toSet());
            departments = departmentRepository.findAllByParentIdIn(ids);
            set.addAll(ids);
        }
        return departmentRepository.deleteAllByIdIn(set);
    }

    @Override
    public Department findById(String id) {
        return departmentRepository.findById(id).orElse(new Department());
    }

    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Override
    public List<Department> findAll(String companyId) {
        return departmentRepository.findAllByCompanyId(companyId);
    }
}
