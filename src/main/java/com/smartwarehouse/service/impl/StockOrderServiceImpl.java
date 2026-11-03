package com.smartwarehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smartwarehouse.entity.Inventory;
import com.smartwarehouse.entity.StockOrder;
import com.smartwarehouse.mapper.StockOrderMapper;
import com.smartwarehouse.service.InventoryService;
import com.smartwarehouse.service.StockOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 出入库 Service 实现
 */
@Service
public class StockOrderServiceImpl extends ServiceImpl<StockOrderMapper, StockOrder> implements StockOrderService {

    @Autowired
    private InventoryService inventoryService;

    @Override
    @Transactional
    public boolean createInbound(StockOrder order) {
        order.setOrderNo(generateOrderNo("IN"));
        order.setOrderType("IN");
        order.setStatus("COMPLETED");
        order.setCreateTime(LocalDateTime.now());
        // 入库：增加库存
        adjustInventory(order.getSkuCode(), order.getQuantity());
        return save(order);
    }

    @Override
    @Transactional
    public boolean createOutbound(StockOrder order) {
        order.setOrderNo(generateOrderNo("OUT"));
        order.setOrderType("OUT");
        order.setStatus("COMPLETED");
        order.setCreateTime(LocalDateTime.now());
        // 出库：减少库存
        adjustInventory(order.getSkuCode(), -order.getQuantity());
        return save(order);
    }

    @Override
    public Page<StockOrder> pageOrders(int pageNum, int pageSize, String orderType) {
        LambdaQueryWrapper<StockOrder> wrapper = new LambdaQueryWrapper<>();
        if (orderType != null && !orderType.isEmpty()) {
            wrapper.eq(StockOrder::getOrderType, orderType);
        }
        wrapper.orderByDesc(StockOrder::getCreateTime);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    private void adjustInventory(String skuCode, int delta) {
        Inventory inventory = inventoryService.getOne(new LambdaQueryWrapper<Inventory>()
                .eq(Inventory::getSkuCode, skuCode));
        if (inventory != null) {
            inventory.setQuantity(inventory.getQuantity() + delta);
            inventoryService.updateById(inventory);
        }
    }

    private String generateOrderNo(String prefix) {
        return prefix + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 4);
    }
}
