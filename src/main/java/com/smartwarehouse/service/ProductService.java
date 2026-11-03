package com.smartwarehouse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smartwarehouse.entity.Product;

/**
 * 商品 Service
 */
public interface ProductService extends IService<Product> {

    /**
     * 分页查询商品
     */
    Page<Product> pageProducts(int pageNum, int pageSize, String keyword);
}
