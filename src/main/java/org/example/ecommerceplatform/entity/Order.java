package org.example.ecommerceplatform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单主表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_order")
public class Order {

    /** 自增主键 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 分布式业务唯一订单号 */
    @TableField("order_no")
    private String orderNo;

    /** 买家用户ID — 关联 tb_user.id */
    @TableField("user_id")
    private Long userId;

    /** 订单总金额 */
    @TableField("total_amount")
    private BigDecimal totalAmount;

    /** 实付金额 */
    @TableField("pay_amount")
    private BigDecimal payAmount;

    /** 状态: 0-待支付, 1-已支付, 2-已取消, 3-已完成 */
    @TableField("status")
    private Integer status;

    /** 支付时间 */
    @TableField("pay_time")
    private LocalDateTime payTime;

    @TableField("created_at")
    private LocalDateTime createdAt;

    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
