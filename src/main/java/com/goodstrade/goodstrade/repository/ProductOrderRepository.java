package com.goodstrade.goodstrade.repository;

import com.goodstrade.goodstrade.entity.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder,Long> {
    List<ProductOrder> findProductOrderByBuyerId(Long buyerId);
    List<ProductOrder> findProductOrderBySellerId(Long id);
}
