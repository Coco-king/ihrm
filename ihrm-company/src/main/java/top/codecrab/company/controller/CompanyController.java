package top.codecrab.company.controller;


import org.springframework.web.bind.annotation.*;

import top.codecrab.common.entity.company.Company;
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
@RequestMapping("/company")
public class CompanyController extends BaseController {

    @GetMapping
    public Result findAll() {
        List<Company> companyList = companyService.findAll();
        return Result.success(companyList);
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable("id") String id) {
        Company company = companyService.findById(id);
        return Result.success(company);
    }

    @PostMapping
    public Result save(@RequestBody Company company) {
        companyService.save(company);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result update(@PathVariable("id") String id, @RequestBody Company company) {
        company.setId(id);
        return companyService.update(company) ? Result.success() : Result.fail();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") String id) {
        companyService.delete(id);
        return Result.success();
    }

    @PutMapping("/audit/{id}/{flag}")
    public Result audit(@PathVariable("id") String id, @PathVariable("flag") Boolean flag) {
        return companyService.audit(id, flag) ? Result.success() : Result.fail();
    }

}
