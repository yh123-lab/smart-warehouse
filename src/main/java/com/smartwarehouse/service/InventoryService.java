package com.smartwarehouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smartwarehouse.entity.Inventory;

/**
 * 库存 Service
 */
public interface InventoryService extends IService<Inventory> {

    /**
     * 锁定库存（防止超卖）
     */
    boolean lockStock(String skuCode, int quantity);

    /**
     * 释放锁定库存
     */
    boolean unlockStock(String skuCode, int quantity);

    /**
     * 获取可用库存
     */
    Integer getAvailableQuantity(String skuCode);
}
