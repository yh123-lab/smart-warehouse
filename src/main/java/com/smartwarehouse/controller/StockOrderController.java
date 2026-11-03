package com.smartwarehouse.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwarehouse.common.Result;
import com.smartwarehouse.entity.StockOrder;
import com.smartwarehouse.service.StockOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 出入库管理 Controller
 */
@RestController
@RequestMapping("/api/stock")
public class StockOrderController {

    @Autowired
    private StockOrderService stockOrderService;

    @PostMapping("/inbound")
    public Result<StockOrder> createInbound(@RequestBody StockOrder order) {
        stockOrderService.createInbound(order);
        return Result.success(order);
    }

    @PostMapping("/outbound")
    public Result<StockOrder> createOutbound(@RequestBody StockOrder order) {
        stockOrderService.createOutbound(order);
        return Result.success(order);
    }

    @GetMapping("/page")
    public Result<Page<StockOrder>> page(@RequestParam(defaultValue = "1") int pageNum,
                                         @RequestParam(defaultValue = "10") int pageSize,
                                         @RequestParam(required = false) String orderType) {
        return Result.success(stockOrderService.pageOrders(pageNum, pageSize, orderType));
    }
}
