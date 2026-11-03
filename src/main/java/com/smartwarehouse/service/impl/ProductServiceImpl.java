package com.smartwarehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smartwarehouse.entity.Product;
import com.smartwarehouse.mapper.ProductMapper;
import com.smartwarehouse.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 商品 Service 实现
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Override
    public Page<Product> pageProducts(int pageNum, int pageSize, String keyword) {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) {
            wrapper.like(Product::getProductName, keyword)
                   .or().like(Product::getSkuCode, keyword)
                   .or().like(Product::getBarcode, keyword);
        }
        wrapper.orderByDesc(Product::getCreateTime);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }
}
