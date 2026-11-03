package com.smartwarehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smartwarehouse.entity.AlertRecord;
import com.smartwarehouse.entity.Inventory;
import com.smartwarehouse.entity.Product;
import com.smartwarehouse.mapper.AlertRecordMapper;
import com.smartwarehouse.mapper.InventoryMapper;
import com.smartwarehouse.mapper.ProductMapper;
import com.smartwarehouse.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 预警 Service 实现
 */
@Service
public class AlertServiceImpl extends ServiceImpl<AlertRecordMapper, AlertRecord> implements AlertService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private InventoryMapper inventoryMapper;

    @Override
    @Scheduled(cron = "0 */30 * * * ?")
    public List<AlertRecord> scanLowStock() {
        List<AlertRecord> alerts = new ArrayList<>();
        // 简化实现：遍历所有库存，对比商品最低阈值
        List<Product> products = productMapper.selectList(null);
        for (Product p : products) {
            if (p.getMinStock() == null) continue;
            Inventory inv = inventoryMapper.selectOne(new LambdaQueryWrapper<Inventory>()
                    .eq(Inventory::getSkuCode, p.getSkuCode()));
            if (inv == null) continue;

            int available = inv.getQuantity() - (inv.getLockedQuantity() == null ? 0 : inv.getLockedQuantity());
            if (available < p.getMinStock()) {
                AlertRecord record = new AlertRecord();
                record.setSkuCode(p.getSkuCode());
                record.setAlertType("LOW_STOCK");
                record.setAlertLevel(calculateLevel(available, p.getMinStock()));
                record.setCurrentStock(available);
                record.setThreshold(p.getMinStock());
                record.setHandled(false);
                record.setCreateTime(LocalDateTime.now());
                save(record);
                alerts.add(record);
            }
        }
        return alerts;
    }

    @Override
    public List<AlertRecord> listUnhandled() {
        return list(new LambdaQueryWrapper<AlertRecord>()
                .eq(AlertRecord::getHandled, false)
                .orderByDesc(AlertRecord::getCreateTime));
    }

    private String calculateLevel(int current, int min) {
        double ratio = (double) current / min;
        if (ratio <= 0.3) return "CRITICAL";
        if (ratio <= 0.6) return "WARNING";
        return "NOTICE";
    }
}
