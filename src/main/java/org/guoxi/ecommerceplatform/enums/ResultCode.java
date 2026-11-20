package org.guoxi.ecommerceplatform.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

    // 通用状态码
    SUCCESS(200, "操作成功"),
    FAILED(500, "系统内部异常"),
    VALIDATE_FAILED(400, "参数校验失败"),
    UNAUTHORIZED(401, "未登录或 Token 已过期"),
    FORBIDDEN(403, "无权限访问"),

    // 电商及秒杀业务扩展状态码
    USER_NOT_FOUND(1001, "用户不存在或密码错误"),
    ACCOUNT_DISABLED(1002, "账号已被禁用，请联系管理员"),
    STOCK_NOT_ENOUGH(2001, "商品库存不足"),
    SECKILL_NOT_STARTED(2002, "秒杀活动尚未开始"),
    SECKILL_ENDED(2003, "秒杀活动已结束"),
    REPEAT_SECKILL(2004, "请勿重复秒杀");

    private final Integer code;
    private final String msg;
}
