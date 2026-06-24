package com.voter.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RespBody<T> {

    /**
     * 自定义业务码
     */
    private String code;
    /**
     * 自定义业务提示说明
     */
    private String msg;
    /**
     * 自定义返回 数据结果集
     */
    private T data;

    public static RespBody<Void> ok() {
        return ok(null);
    }

    public static <T> RespBody<T> ok(T data) {
        return build(RespCode.OK, data);
    }

    public static <T> RespBody<T> okWith(String msg) {
        return build(RespCode.OK.getCode(), msg, null);
    }

    public static RespBody<ErrorBody> error(String code, String msg, ErrorBody errorBody) {
        return build(code, msg, errorBody);
    }

    public static RespBody<ErrorBody> error(ResultCode resultCode, ErrorBody errorBody) {
        return build(resultCode.getCode(), resultCode.getMessage(), errorBody);
    }

    public static RespBody<ErrorBody> error(ResultCode resultCode, String msg, ErrorBody errorBody) {
        return build(resultCode.getCode(), msg, errorBody);
    }

    public static <T> RespBody<T> fail(String msg) {
        return fail(RespCode.FAIL, msg, null);
    }

    public static <T> RespBody<T> fail(ResultCode resultCode) {
        return fail(resultCode, resultCode.getMessage(), null);
    }

    public static <T> RespBody<T> fail(ResultCode resultCode, String msg, T data) {
        return build(resultCode.getCode(), msg, data);
    }


    public static <T> RespBody<T> build(ResultCode resultCode, T data) {
        return build(resultCode.getCode(), resultCode.getMessage(), data);
    }

    /**
     * 以上所有构建均调用此底层方法
     *
     * @param stateCode 状态值
     * @param msg       返回消息
     * @param data      返回数据体
     */
    public static <T> RespBody<T> build(String stateCode, String msg, T data) {
        return new RespBody<>(stateCode, msg, data);
    }


    /**
     * @return 是否成功请求处理
     */
    public boolean isOk() {
        return RespCode.OK.getCode().equals(getCode());
    }
}
