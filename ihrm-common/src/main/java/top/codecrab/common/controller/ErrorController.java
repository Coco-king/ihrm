package top.codecrab.common.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.codecrab.common.response.Result;

/**
 * @author codecrab
 * @since 2021年04月19日 18:09
 */
@RestController
public class ErrorController {

    @RequestMapping("/authError")
    public Result authError(int code) {
        return code == 1 ? Result.fail("您还未登录") : Result.fail("权限不足");
    }
}
