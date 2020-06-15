package com.goodstrade.goodstrade.validator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goodstrade.goodstrade.entity.Image;
import com.goodstrade.goodstrade.entity.Product;
import com.goodstrade.goodstrade.entity.User;
import com.goodstrade.goodstrade.repository.ImageRepository;
import com.goodstrade.goodstrade.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class AddProductValidator implements Validator {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Product product = (Product) target;

        List<Long> imageIds = null;

        try {
            imageIds = objectMapper.readValue(product.getImageStr(), new TypeReference<List<Long>>(){});
        } catch (JsonProcessingException e) {
            errors.rejectValue("image", null, "This image already exists");
        }

        // Check Image is not exists.
        if (imageIds != null) {
            for(Long imageId: imageIds) {
                Optional<Image> image = imageRepository.findById(imageId);
                if(!image.isPresent()){
                    errors.rejectValue("image", null, "This image already exists");
                }
            }
        }

    }
}
