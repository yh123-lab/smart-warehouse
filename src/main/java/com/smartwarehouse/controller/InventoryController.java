package com.smartwarehouse.controller;

import com.smartwarehouse.common.Result;
import com.smartwarehouse.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 库存管理 Controller
 */
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/available/{skuCode}")
    public Result<Integer> getAvailable(@PathVariable String skuCode) {
        return Result.success(inventoryService.getAvailableQuantity(skuCode));
    }

    @PostMapping("/lock")
    public Result<Boolean> lock(@RequestParam String skuCode, @RequestParam int quantity) {
        return Result.success(inventoryService.lockStock(skuCode, quantity));
    }

    @PostMapping("/unlock")
    public Result<Boolean> unlock(@RequestParam String skuCode, @RequestParam int quantity) {
        return Result.success(inventoryService.unlockStock(skuCode, quantity));
    }
}
