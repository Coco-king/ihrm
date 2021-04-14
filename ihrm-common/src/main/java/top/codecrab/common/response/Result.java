package top.codecrab.common.response;

import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author codecrab
 */
@Data
@NoArgsConstructor
public class Result {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 返回码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回数据
     */
    private Object data;

    public Result(ResultCode code) {
        this.success = code.success;
        this.code = code.code;
        this.message = code.message;
    }

    public Result(ResultCode code, Object data) {
        this.success = code.success;
        this.code = code.code;
        this.message = code.message;
        this.data = data;
    }

    public Result(Integer code, String message, boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }

    public static Result success() {
        return new Result(ResultCode.SUCCESS);
    }

    public static Result success(Object data) {
        return new Result(ResultCode.SUCCESS, data);
    }

    public static Result error() {
        return new Result(ResultCode.SERVER_ERROR);
    }

    public static Result fail() {
        return new Result(ResultCode.FAIL);
    }

    public static Result fail(String msg) {
        return new Result(10001, msg, false);
    }
}
