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
 * 秒杀活动表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_seckill_activity")
public class SeckillActivity {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 关联商品ID — 关联 tb_product.id */
    @TableField("product_id")
    private Long productId;

    /** 秒杀特价 */
    @TableField("seckill_price")
    private BigDecimal seckillPrice;

    /** 秒杀专属库存 */
    @TableField("seckill_stock")
    private Integer seckillStock;

    /** 开始时间 */
    @TableField("start_time")
    private LocalDateTime startTime;

    /** 结束时间 */
    @TableField("end_time")
    private LocalDateTime endTime;

    /** 状态: 0-未上线, 1-上线, 2-已结束 */
    @TableField("status")
    private Integer status;
}
