package com.smartwarehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smartwarehouse.entity.Inventory;
import com.smartwarehouse.mapper.InventoryMapper;
import com.smartwarehouse.service.InventoryService;
import org.springframework.stereotype.Service;

/**
 * 库存 Service 实现
 */
@Service
public class InventoryServiceImpl extends ServiceImpl<InventoryMapper, Inventory> implements InventoryService {

    @Override
    public boolean lockStock(String skuCode, int quantity) {
        Inventory inventory = getOne(new LambdaQueryWrapper<Inventory>()
                .eq(Inventory::getSkuCode, skuCode));
        if (inventory == null) return false;

        int available = inventory.getQuantity() - (inventory.getLockedQuantity() == null ? 0 : inventory.getLockedQuantity());
        if (available < quantity) return false;

        inventory.setLockedQuantity((inventory.getLockedQuantity() == null ? 0 : inventory.getLockedQuantity()) + quantity);
        return updateById(inventory);
    }

    @Override
    public boolean unlockStock(String skuCode, int quantity) {
        Inventory inventory = getOne(new LambdaQueryWrapper<Inventory>()
                .eq(Inventory::getSkuCode, skuCode));
        if (inventory == null || inventory.getLockedQuantity() == null) return false;

        inventory.setLockedQuantity(Math.max(0, inventory.getLockedQuantity() - quantity));
        return updateById(inventory);
    }

    @Override
    public Integer getAvailableQuantity(String skuCode) {
        Inventory inventory = getOne(new LambdaQueryWrapper<Inventory>()
                .eq(Inventory::getSkuCode, skuCode));
        if (inventory == null) return 0;
        return inventory.getQuantity() - (inventory.getLockedQuantity() == null ? 0 : inventory.getLockedQuantity());
    }
}
