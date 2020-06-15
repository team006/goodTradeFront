package com.goodstrade.goodstrade.service.product;

import com.goodstrade.goodstrade.entity.Product;
import com.goodstrade.goodstrade.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImp implements ProductSerivce {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    @Override
    public Product getProductDetailById(Long id) {
        return productRepository.findProductById(id);
    }

    @Override
    public List<Product> getProductBySellerId(Long id){
        return productRepository.findProductBySellerId(id);
    }
}
