package com.goodstrade.goodstrade.service.productOrder;

import com.goodstrade.goodstrade.entity.ProductOrder;
import com.goodstrade.goodstrade.repository.ProductOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProductOrderServiceImp implements ProductOrderService {

    @Autowired
    private ProductOrderRepository productOrderRepository;

    @Override
    public List<ProductOrder> getProductOrderByBuyerId(Long buyerId){
        return productOrderRepository.findProductOrderByBuyerId(buyerId);
    }

    @Override
    public List<ProductOrder> getProductOrderBySellerId(Long sellerId){
        return productOrderRepository.findProductOrderBySellerId(sellerId);
    }

}
