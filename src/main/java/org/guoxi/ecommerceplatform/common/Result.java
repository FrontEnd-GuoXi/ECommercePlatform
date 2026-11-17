package org.guoxi.ecommerceplatform.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.guoxi.ecommerceplatform.enums.ResultCode;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 业务状态码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 响应数据载荷
     */
    private T data;

    // ==================== 成功响应静态方法 ====================

    /**
     * 成功（无返回数据，如删除、修改操作）
     */
    public static <T> Result<T> success() {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), null);
    }

    /**
     * 成功（携带返回数据，最常用）
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), data);
    }

    /**
     * 成功（自定义提示信息 + 返回数据）
     */
    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(), msg, data);
    }

    // ==================== 失败响应静态方法 ====================

    /**
     * 失败（默认系统异常）
     */
    public static <T> Result<T> fail() {
        return new Result<>(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMsg(), null);
    }

    /**
     * 失败（自定义错误提示，默认状态码 500）
     */
    public static <T> Result<T> fail(String msg) {
        return new Result<>(ResultCode.FAILED.getCode(), msg, null);
    }

    /**
     * 失败（使用预定义状态码枚举）
     */
    public static <T> Result<T> fail(ResultCode resultCode) {
        return new Result<>(resultCode.getCode(), resultCode.getMsg(), null);
    }

    /**
     * 失败（自定义状态码 + 自定义错误提示，适合参数校验等场景）
     */
    public static <T> Result<T> fail(Integer code, String msg) {
        return new Result<>(code, msg, null);
    }
}