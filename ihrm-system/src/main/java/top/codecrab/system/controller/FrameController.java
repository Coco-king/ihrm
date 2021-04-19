package top.codecrab.system.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.aliyuncs.exceptions.ClientException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;
import top.codecrab.common.entity.system.ProfileResult;
import top.codecrab.common.entity.system.User;
import top.codecrab.common.response.Result;
import top.codecrab.system.base.BaseController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author codecrab
 * @since 2021年04月15日 16:11
 */
@CrossOrigin
@RestController
@RequestMapping("/frame")
public class FrameController extends BaseController {

    private final String prefix = "USER_CODE:";

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody Map<String, String> map) {
        String mobile = map.get("mobile");
        String password = map.get("password");
        if (StrUtil.hasBlank(password, mobile)) {
            return Result.fail("密码或手机号不能为空");
        }

        try {
            //此处需要先把密码加密之后传过去才可以
            password = new Md5Hash(password, mobile, 3).toString();
            UsernamePasswordToken token = new UsernamePasswordToken(mobile, password);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            String sessionId = (String) subject.getSession().getId();
            return Result.success(MapUtil.of("token", sessionId));
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return Result.fail("用户名或密码错误");
        }
    }


    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public Result profile() {
        //获取shiro的安全数据
        ProfileResult profileResult = (ProfileResult) SecurityUtils.getSubject().getPrincipal();
        return Result.success(profileResult);
    }

    @RequestMapping(value = "/register/verification_code", method = RequestMethod.POST)
    public Result verificationCode(@RequestBody Map<String, String> phone) {
        String mobile = phone.get("mobile");
        if (StrUtil.isBlank(mobile)) {
            return Result.fail("发送验证码失败");
        }

        String code = RandomUtil.randomNumbers(6);
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(30 * 60);
        session.setAttribute(prefix + mobile, code);

        try {
            smsUtils.sendSms(mobile, code, smsProperties.getSignName(), smsProperties.getVerifyCodeTemplate());
        } catch (ClientException e) {
            e.printStackTrace();
            return Result.fail("发送验证码失败");
        }
        return new Result(10000, "发送验证码成功", true);
    }

    @RequestMapping(value = "/register/step1", method = RequestMethod.POST)
    public Result step1(@RequestBody Map<String, String> map) {
        String mobile = map.get("mobile");
        String verificationCode = map.get("verificationCode");
        String password = map.get("password");
        String confirmPassword = map.get("confirmPassword");

        if (!StrUtil.equals(confirmPassword, password)) {
            return Result.fail("两次输入的密码不一致");
        }

        HttpSession session = request.getSession();
        String code = (String) session.getAttribute(prefix + mobile);
        if (!StrUtil.equals(verificationCode, code)) {
            return Result.fail("验证码不正确");
        }

        return new Result(10000, mobile, true);
    }

    @RequestMapping(value = "/register/step2", method = RequestMethod.POST)
    public Result step2(@RequestBody Map<String, String> map) {
        User user1 = BeanUtil.toBean(map, User.class);

        String mobile = map.get("mobile");
        String verificationCode = map.get("verificationCode");
        String password = map.get("password");
        String confirmPassword = map.get("confirmPassword");

        if (!StrUtil.equals(confirmPassword, password)) {
            return Result.fail("两次输入的密码不一致");
        }

        HttpSession session = request.getSession();
        String code = (String) session.getAttribute(prefix + mobile);
        session.removeAttribute(prefix + mobile);
        if (!StrUtil.equals(verificationCode, code)) {
            return Result.fail("验证码不正确");
        }

        User user = userService.findByMobile(mobile);
        if (user != null) {
            return Result.fail("手机号已注册");
        }

        userService.save(user1);
        return Result.success();
    }
}
