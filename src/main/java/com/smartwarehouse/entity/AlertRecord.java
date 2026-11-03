package com.smartwarehouse.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 预警记录实体类
 */
@Data
@TableName("alert_record")
public class AlertRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /** SKU编码 */
    private String skuCode;

    /** 预警类型 */
    private String alertType;

    /** 预警级别 */
    private String alertLevel;

    /** 当前库存 */
    private Integer currentStock;

    /** 阈值 */
    private Integer threshold;

    /** 是否已处理 */
    private Boolean handled;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
