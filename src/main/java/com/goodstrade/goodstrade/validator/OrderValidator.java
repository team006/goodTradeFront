package com.goodstrade.goodstrade.validator;

import com.goodstrade.goodstrade.entity.Product;
import com.goodstrade.goodstrade.entity.ProductOrder;
import com.goodstrade.goodstrade.entity.User;
import com.goodstrade.goodstrade.repository.ProductOrderRepository;
import com.goodstrade.goodstrade.repository.ProductRepository;
import com.goodstrade.goodstrade.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class OrderValidator implements Validator {

    @Autowired
    private ProductRepository productRepository;
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductOrder request = (ProductOrder) target;

        //Find Product From Repository
        Product product = productRepository.findProductById(request.getProduct().getId());

        // Check if request must not more than Quantity.
        if (request.getQuantity() > product.getQuantity()){
            errors.rejectValue("quatity", null, "ปริมาณสั่งซื้อมากกว่าจำนวนสินค้า กรุณากรอกจำนวนใหม่อีกครั้ง");
        }

    }

    public void validate(Object target, Errors errors , Long checkId){
        ProductOrder request = (ProductOrder) target;

        //Find Product From Repository
        Product product = productRepository.findProductById(checkId);

        // Check if request must not more than Quantity.
        if (request.getQuantity() > product.getQuantity()){
            errors.rejectValue("quantity", null, "ปริมาณสั่งซื้อมากกว่าจำนวนสินค้า กรุณากรอกจำนวนใหม่อีกครั้ง");
        }
    }


}