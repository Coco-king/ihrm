package top.codecrab.system.controller;

import org.springframework.web.bind.annotation.*;
import top.codecrab.common.entity.system.Permission;
import top.codecrab.common.response.Result;
import top.codecrab.common.response.ResultCode;
import top.codecrab.system.base.BaseController;

import java.util.List;

/**
 * @author codecrab
 * @since 2021年04月13日 15:13
 */
@CrossOrigin
@RestController
@RequestMapping("/sys/permission")
public class PermissionController extends BaseController {

    @PostMapping
    public Result add(@RequestBody Permission permission) {
        permissionService.save(permission);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result update(
            @PathVariable(name = "id") String id,
            @RequestBody Permission permission
    ) {
        permission.setId(id);
        boolean update = permissionService.update(permission);
        return update ? Result.success() : Result.fail();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable(name = "id") String id) {
        permissionService.delete(id);
        return Result.success();
    }

    /**
     * 根据ID获取角色信息
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable(name = "id") String id) {
        Permission permission = permissionService.findById(id);
        return new Result(ResultCode.SUCCESS, permission);
    }

    /**
     * 分页查询角色
     */
    @GetMapping
    public Result findByPage(
            @RequestParam(defaultValue = "0") int type,
            @RequestParam(defaultValue = "") String pid
    ) {
        List<Permission> searchPage = permissionService.findAll(type, pid);
        return new Result(ResultCode.SUCCESS, searchPage);
    }
}
