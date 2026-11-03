package com.smartwarehouse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smartwarehouse.entity.StockOrder;

/**
 * 出入库 Service
 */
public interface StockOrderService extends IService<StockOrder> {

    /**
     * 创建入库单
     */
    boolean createInbound(StockOrder order);

    /**
     * 创建出库单
     */
    boolean createOutbound(StockOrder order);

    /**
     * 分页查询
     */
    Page<StockOrder> pageOrders(int pageNum, int pageSize, String orderType);
}
