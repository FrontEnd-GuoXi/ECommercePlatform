package org.example.ecommerceplatform.entity;

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
 * 商品表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_product")
public class Product {

    /** 商品ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 商品标题 */
    @TableField("title")
    private String title;

    /** 分类ID — 关联分类表 */
    @TableField("category_id")
    private Long categoryId;

    /** 销售原价 */
    @TableField("price")
    private BigDecimal price;

    /** 商品主图 */
    @TableField("pic_url")
    private String picUrl;

    /** 商品详情 */
    @TableField("detail")
    private String detail;

    /** 上架状态: 0-下架, 1-上架 */
    @TableField("status")
    private Integer status;

    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
