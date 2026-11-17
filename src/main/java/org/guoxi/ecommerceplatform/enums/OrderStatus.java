package org.guoxi.ecommerceplatform.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * 订单状态
 */
@Getter
public enum OrderStatus {

    /** 待支付 */
    PENDING_PAYMENT(0, "待支付"),

    /** 已支付 */
    PAID(1, "已支付"),

    /** 已取消 */
    CANCELLED(2, "已取消"),

    /** 已完成 */
    COMPLETED(3, "已完成");

    /** 数据库存储值 */
    @EnumValue
    private final Integer code;

    /** 状态描述 */
    private final String description;

    OrderStatus(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
}
