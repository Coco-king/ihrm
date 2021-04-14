package top.codecrab.common.response;

import lombok.AllArgsConstructor;

/**
 * @author codecrab
 */
@AllArgsConstructor
public enum ResultCode {

    //操作成功
    SUCCESS(true, 10000, "操作成功！"),
    //保存成功
    SAVE_SUCCESS(true, 10000, "保存成功！"),
    //更新成功
    UPDATE_SUCCESS(true, 10000, "更新成功！"),
    //删除成功
    DELETE_SUCCESS(true, 10000, "删除成功！"),

    //---系统错误返回码-----
    //操作失败
    FAIL(false, 10001, "操作失败"),
    //未登录
    UNAUTHENTICATED(false, 10002, "您还未登录"),
    //权限不足
    UNAUTHORISED(false, 10003, "权限不足"),
    //系统繁忙
    SERVER_ERROR(false, 99999, "抱歉，系统繁忙，请稍后重试！");

    //---用户操作返回码----
    //---企业操作返回码----
    //---权限操作返回码----
    //---其他操作返回码----

    /**
     * 操作是否成功
     */
    boolean success;

    /**
     * 操作代码
     */
    int code;

    /**
     * 提示信息
     */
    String message;

    public boolean success() {
        return success;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }

}
