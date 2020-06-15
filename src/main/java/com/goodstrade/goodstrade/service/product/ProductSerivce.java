package com.goodstrade.goodstrade.service.product;

import com.goodstrade.goodstrade.entity.Product;

import java.util.List;

public interface ProductSerivce {
    List<Product> getAllProducts();
    Product getProductDetailById(Long id);
    List<Product> getProductBySellerId(Long id);
}
