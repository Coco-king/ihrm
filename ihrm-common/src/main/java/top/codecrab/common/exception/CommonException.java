package top.codecrab.common.exception;

import lombok.Getter;
import top.codecrab.common.response.ResultCode;

/**
 * 自定义异常
 *
 * @author codecrab
 */
@Getter
public class CommonException extends Exception {

    private final ResultCode resultCode;

    public CommonException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
