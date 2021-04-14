package top.codecrab.company.base;

import org.springframework.beans.factory.annotation.Autowired;
import top.codecrab.common.utils.IdWorker;
import top.codecrab.company.repository.CompanyRepository;
import top.codecrab.company.repository.DepartmentRepository;

/**
 * @author codecrab
 * @since 2021年04月09日 11:16
 */
public class BaseService {

    protected static final String NULL = "null";

    @Autowired
    protected IdWorker idWorker;
    @Autowired
    protected CompanyRepository companyRepository;
    @Autowired
    protected DepartmentRepository departmentRepository;
}
