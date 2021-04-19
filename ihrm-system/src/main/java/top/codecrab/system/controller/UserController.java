package top.codecrab.system.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import top.codecrab.common.config.Constants;
import top.codecrab.common.entity.system.Role;
import top.codecrab.common.entity.system.User;
import top.codecrab.common.response.InnerResult;
import top.codecrab.common.response.PageResult;
import top.codecrab.common.response.Result;
import top.codecrab.common.response.ResultCode;
import top.codecrab.system.base.BaseController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author codecrab
 * @since 2021年04月13日 9:13
 */
@CrossOrigin
@RestController
@RequestMapping("/sys/user")
public class UserController extends BaseController {

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size,
            @RequestParam(value = "companyId", required = false) String companyId,
            @RequestParam(value = "departmentId", required = false) String departmentId,
            @RequestParam(value = "hasDept", required = false) String hasDept
    ) {
        companyId = Constants.COMPANY_ID;
        Page<User> userPage = userService.findAll(companyId, departmentId, hasDept, page, size);
        PageResult<User> pageResult = new PageResult<>(userPage.getTotalElements(), userPage.getContent());
        return Result.success(pageResult);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable("id") String id) {
        User user = userService.findById(id);
        //由于前端需要默认显示已经拥有的角色，需要已经拥有的角色id列表，在这里构建
        List<String> ids = user.getRoles().stream().map(Role::getId).collect(Collectors.toList());
        return new InnerResult<>(ResultCode.SUCCESS, user, ids);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody User user) {
        user.setCompanyId(Constants.COMPANY_ID);
        user.setCompanyName(Constants.COMPANY_NAME);
        user.setEnableState(0);
        userService.save(user);
        return Result.success();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@PathVariable("id") String id, @RequestBody User user) {
        user.setId(id);
        return userService.update(user) ? Result.success() : Result.fail();
    }

    /**
     * 使用Mapping注解的name属性进行权限控制
     * RequiresPermissions：权限控制api标识包含API-USER-DELETE才可以删除
     */
    @RequiresPermissions("API-USER-DELETE")
    @RequestMapping(value = "/{id}", name = "API-USER-DELETE", method = RequestMethod.DELETE)
    public Result delete(@PathVariable("id") String id) {
        userService.delete(id);
        return Result.success();
    }

    @RequestMapping(value = "/assignRoles", method = RequestMethod.PUT)
    public Result assignRoles(@RequestBody Map<String, Object> map) {
        boolean isSuccess = userService.assignRoles(map);
        return isSuccess ? Result.success() : Result.fail();
    }

    @RequestMapping(value = "/toggleStatus/{id}", method = RequestMethod.PUT)
    public Result toggleStatus(@PathVariable("id") String id) {
        boolean isSuccess = userService.toggleStatus(id);
        return isSuccess ? Result.success() : Result.fail();
    }

}
