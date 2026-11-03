package com.smartwarehouse.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 库存实体类
 */
@Data
@TableName("inventory")
public class Inventory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /** SKU编码 */
    private String skuCode;

    /** 仓库ID */
    private Long warehouseId;

    /** 货架编码 */
    private String shelfCode;

    /** 实际库存 */
    private Integer quantity;

    /** 锁定库存 */
    private Integer lockedQuantity;

    /** 最后盘点时间 */
    private LocalDateTime lastCheckTime;

    /** 乐观锁版本号 */
    @Version
    private Integer version;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
