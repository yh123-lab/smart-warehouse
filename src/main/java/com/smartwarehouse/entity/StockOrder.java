package com.smartwarehouse.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 出入库单实体类
 */
@Data
@TableName("stock_order")
public class StockOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 单据编号 */
    private String orderNo;

    /** 类型：IN-入库 OUT-出库 */
    private String orderType;

    /** 关联SKU */
    private String skuCode;

    /** 数量 */
    private Integer quantity;

    /** 状态 */
    private String status;

    /** 操作人 */
    private String operator;

    /** 备注 */
    private String remark;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
