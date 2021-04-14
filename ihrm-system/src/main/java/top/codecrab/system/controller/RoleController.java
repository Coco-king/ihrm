package top.codecrab.system.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import top.codecrab.common.config.Constants;
import top.codecrab.common.entity.system.Permission;
import top.codecrab.common.entity.system.Role;
import top.codecrab.common.response.InnerResult;
import top.codecrab.common.response.PageResult;
import top.codecrab.common.response.Result;
import top.codecrab.common.response.ResultCode;
import top.codecrab.system.base.BaseController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 20428
 */
@CrossOrigin
@RestController
@RequestMapping("/sys/role")
public class RoleController extends BaseController {

    /**
     * 查询角色
     */
    @GetMapping("/findAll")
    public Result findAll() {
        List<Role> roles = roleService.findAll();
        return new Result(ResultCode.SUCCESS, roles);
    }

    @PostMapping
    public Result add(@RequestBody Role role) {
        role.setCompanyId(Constants.COMPANY_ID);
        roleService.save(role);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result update(
            @PathVariable(name = "id") String id,
            @RequestBody Role role
    ) {
        role.setId(id);
        boolean update = roleService.update(role);
        return update ? Result.success() : Result.fail();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable(name = "id") String id) {
        roleService.delete(id);
        return Result.success();
    }

    /**
     * 根据ID获取角色信息
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable(name = "id") String id) {
        Role role = roleService.findById(id);
        //由于前端需要默认显示已经拥有的权限，需要已经拥有的权限id列表，在这里构建
        List<String> ids = role.getPermissions().stream().map(Permission::getId).collect(Collectors.toList());
        return new InnerResult<>(ResultCode.SUCCESS, role, ids);
    }

    /**
     * 分页查询角色
     */
    @GetMapping
    public Result findByPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<Role> searchPage = roleService.findAll(Constants.COMPANY_ID, page, size);
        PageResult<Role> pr = new PageResult<>(searchPage.getTotalElements(), searchPage.getContent());
        return new Result(ResultCode.SUCCESS, pr);
    }

    @PutMapping("/assignPrem")
    public Result assignPrem(@RequestBody Map<String, Object> map) {
        boolean assignPrem = roleService.assignPrem(map);
        return assignPrem ? Result.success() : Result.fail();
    }
}
