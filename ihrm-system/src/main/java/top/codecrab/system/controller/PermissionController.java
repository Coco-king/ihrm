package top.codecrab.system.controller;

import org.springframework.web.bind.annotation.*;
import top.codecrab.common.entity.system.Permission;
import top.codecrab.common.response.Result;
import top.codecrab.common.response.ResultCode;
import top.codecrab.system.base.BaseController;

import java.util.List;
import java.util.Map;

/**
 * @author codecrab
 * @since 2021年04月13日 15:13
 */
@CrossOrigin
@RestController
@RequestMapping("/sys/permission")
public class PermissionController extends BaseController {

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

    /**
     * 根据ID获取角色信息
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable(name = "id") String id) {
        Object permission = permissionService.findById(id);
        return new Result(ResultCode.SUCCESS, permission);
    }

    @PostMapping
    public Result save(@RequestBody Map<String, Object> map) {
        permissionService.save(map);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result update(
            @PathVariable(name = "id") String id,
            @RequestBody Map<String, Object> map
    ) {
        map.put("id", id);
        boolean update = permissionService.update(map);
        return update ? Result.success() : Result.fail();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable(name = "id") String id) {
        permissionService.delete(id);
        return Result.success();
    }
}
