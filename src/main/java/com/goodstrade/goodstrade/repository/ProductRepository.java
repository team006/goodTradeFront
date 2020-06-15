package com.goodstrade.goodstrade.repository;

import com.goodstrade.goodstrade.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Product findProductById(Long id);
    List<Product> findProductBySellerId(Long id);
    Product findByImage(String image);
}
