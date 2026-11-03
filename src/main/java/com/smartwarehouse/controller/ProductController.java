package com.smartwarehouse.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwarehouse.common.Result;
import com.smartwarehouse.entity.Product;
import com.smartwarehouse.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商品管理 Controller
 */
@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public Result<Product> create(@RequestBody Product product) {
        productService.save(product);
        return Result.success(product);
    }

    @PutMapping("/{id}")
    public Result<Boolean> update(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);
        return Result.success(productService.updateById(product));
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(productService.removeById(id));
    }

    @GetMapping("/{id}")
    public Result<Product> getById(@PathVariable Long id) {
        return Result.success(productService.getById(id));
    }

    @GetMapping("/page")
    public Result<Page<Product>> page(@RequestParam(defaultValue = "1") int pageNum,
                                      @RequestParam(defaultValue = "10") int pageSize,
                                      @RequestParam(required = false) String keyword) {
        return Result.success(productService.pageProducts(pageNum, pageSize, keyword));
    }
}
