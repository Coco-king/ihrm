package top.codecrab.common.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.codecrab.common.exception.CommonException;
import top.codecrab.common.response.Result;
import top.codecrab.common.response.ResultCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义的公共异常处理器
 * 1.声明异常处理器
 * 2.对异常统一处理
 *
 * @author codecrab
 */
@ControllerAdvice
public class BaseExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result error(HttpServletRequest request, HttpServletResponse response, Exception e) {
        e.printStackTrace();
        if (e instanceof CommonException) {
            //类型转型
            CommonException ce = (CommonException) e;
            return new Result(ce.getResultCode());
        } else {
            return new Result(ResultCode.SERVER_ERROR);
        }
    }
}
