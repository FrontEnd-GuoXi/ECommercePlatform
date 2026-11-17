package org.guoxi.ecommerceplatform.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
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
 * 订单明细表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_order_item")
public class OrderItem {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 关联业务订单号 — 关联 tb_order.order_no */
    @TableField("order_no")
    private String orderNo;

    /** 商品ID — 关联 tb_product.id */
    @TableField("product_id")
    private Long productId;

    /** 快照商品名 */
    @TableField("product_name")
    private String productName;

    /** 快照单价 */
    @TableField("price")
    private BigDecimal price;

    /** 购买数量 */
    @TableField("quantity")
    private Integer quantity;

    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
