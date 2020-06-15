package com.goodstrade.goodstrade.service.productOrder;

import com.goodstrade.goodstrade.entity.ProductOrder;

import java.util.List;

public interface ProductOrderService {
    List<ProductOrder> getProductOrderByBuyerId(Long buyerId);
    List<ProductOrder> getProductOrderBySellerId(Long sellerId);
}