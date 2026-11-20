package org.guoxi.ecommerceplatform.common;

import lombok.extern.slf4j.Slf4j;
import org.guoxi.ecommerceplatform.enums.ResultCode;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 * <p>
 * 把业务异常、参数校验失败、请求体解析失败以及未预期异常统一转换为 Result 结构，
 * 保证前端拿到的错误响应格式与正常响应一致。
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常：由 Service 层主动抛出
     */
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException e) {
        log.warn("业务异常: code={}, msg={}", e.getCode(), e.getMessage());
        return Result.fail(e.getCode(), e.getMessage());
    }

    /**
     * 请求体参数校验失败（@Valid / @Validated）
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse(ResultCode.VALIDATE_FAILED.getMsg());
        log.warn("参数校验失败: {}", msg);
        return Result.fail(ResultCode.VALIDATE_FAILED.getCode(), msg);
    }

    /**
     * 请求体无法解析（非 JSON、字段类型不匹配等）
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<Void> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        log.warn("请求体解析失败: {}", e.getMessage());
        return Result.fail(ResultCode.VALIDATE_FAILED.getCode(), "请求体格式错误");
    }

    /**
     * 兜底异常：日志记录完整堆栈，响应只返回统一提示
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error("系统异常", e);
        return Result.fail(ResultCode.FAILED);
    }
}
