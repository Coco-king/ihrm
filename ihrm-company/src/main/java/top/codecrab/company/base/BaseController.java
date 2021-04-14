package top.codecrab.company.base;

import org.springframework.beans.factory.annotation.Autowired;
import top.codecrab.company.service.CompanyService;
import top.codecrab.company.service.DepartmentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author codecrab
 * @date 2021年04月09日 9:29
 */
public class BaseController {
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;
    @Autowired
    protected CompanyService companyService;
    @Autowired
    protected DepartmentService departmentService;
}
