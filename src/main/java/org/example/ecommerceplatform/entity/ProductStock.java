package org.example.ecommerceplatform.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 商品库存表
 * 拆分高频写字段，降低行级锁对商品主表读的影响
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_product_stock")
public class ProductStock {

    /** 商品ID — 关联 tb_product.id，主键 */
    @TableId
    private Long productId;

    /** 可用可售库存 */
    @TableField("available_stock")
    private Integer availableStock;

    /** 下单锁定的库存 */
    @TableField("locked_stock")
    private Integer lockedStock;

    /** 乐观锁版本号 */
    @Version
    @TableField("version")
    private Integer version;

    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
