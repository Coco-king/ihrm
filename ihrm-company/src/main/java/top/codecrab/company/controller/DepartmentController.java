package top.codecrab.company.controller;


import org.springframework.web.bind.annotation.*;

import top.codecrab.common.config.Constants;
import top.codecrab.common.entity.company.Company;
import top.codecrab.common.entity.company.Department;
import top.codecrab.common.response.DeptListResult;
import top.codecrab.common.response.Result;
import top.codecrab.company.base.BaseController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author codecrab
 * @since 2021-04-09
 */
@CrossOrigin
@RestController
@RequestMapping("/company/department")
public class DepartmentController extends BaseController {

    @GetMapping
    public Result findAll() {
        List<Department> departmentList;
        if (Constants.COMPANY_ID == null) {
            departmentList = departmentService.findAll();
        } else {
            departmentList = departmentService.findAll(Constants.COMPANY_ID);
        }
        Company company = companyService.findById(Constants.COMPANY_ID);
        DeptListResult result = new DeptListResult(company, departmentList);
        return Result.success(result);
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable("id") String id) {
        Department department = departmentService.findById(id);
        return Result.success(department);
    }

    @PostMapping
    public Result save(@RequestBody Department department) {
        department.setCompanyId("1");
        departmentService.save(department);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result update(@PathVariable("id") String id, @RequestBody Department department) {
        department.setId(id);
        boolean update = departmentService.update(department);
        return update ? Result.success() : Result.fail();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") String id) {
        int deleteCount = departmentService.delete(id);
        return Result.success(deleteCount);
    }
}
