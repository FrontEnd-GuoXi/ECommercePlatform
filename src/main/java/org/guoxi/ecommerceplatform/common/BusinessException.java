package org.guoxi.ecommerceplatform.common;

import lombok.Getter;
import org.guoxi.ecommerceplatform.enums.ResultCode;

/**
 * 业务异常
 * <p>
 * 业务校验失败时抛出，由 GlobalExceptionHandler 统一转换为 Result 响应，
 * 避免 Service 层直接依赖响应结构。
 */
@Getter
public class BusinessException extends RuntimeException {

    /** 业务状态码 */
    private final Integer code;

    public BusinessException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.code = resultCode.getCode();
    }

    public BusinessException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }
}
