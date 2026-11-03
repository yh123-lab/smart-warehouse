package com.smartwarehouse.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品实体类
 */
@Data
@TableName("product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /** SKU编码（唯一） */
    private String skuCode;

    /** 商品名称 */
    private String productName;

    /** 分类ID */
    private Long categoryId;

    /** 计量单位 */
    private String unit;

    /** 单重(kg) */
    private BigDecimal weight;

    /** 体积(m³) */
    private BigDecimal volume;

    /** 最低库存阈值 */
    private Integer minStock;

    /** 最高库存阈值 */
    private Integer maxStock;

    /** 条形码 */
    private String barcode;

    /** 商品描述 */
    private String description;

    /** 状态：0下架 1上架 */
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}
