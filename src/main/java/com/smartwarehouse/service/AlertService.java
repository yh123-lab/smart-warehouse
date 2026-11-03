package com.smartwarehouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smartwarehouse.entity.AlertRecord;

import java.util.List;

/**
 * 预警 Service
 */
public interface AlertService extends IService<AlertRecord> {

    /**
     * 扫描并生成低库存预警
     */
    List<AlertRecord> scanLowStock();

    /**
     * 查询未处理的预警
     */
    List<AlertRecord> listUnhandled();
}
