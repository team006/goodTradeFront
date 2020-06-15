package com.goodstrade.goodstrade.controller;

import com.goodstrade.goodstrade.entity.ProductOrder;
import com.goodstrade.goodstrade.entity.User;
import com.goodstrade.goodstrade.repository.ProductOrderRepository;
import com.goodstrade.goodstrade.service.productOrder.ProductOrderServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductOrderController {

    @Autowired
    private ProductOrderServiceImp productOrderServiceImp;
    @Autowired
    private ProductOrderRepository productOrderRepository;

    @GetMapping("/orderBuyer")
    public String getProductOrderByBuyerId(ModelMap model){
        User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ProductOrder> productOrderByBuyerId = productOrderServiceImp.getProductOrderByBuyerId(user.getId());
        model.addAttribute("productOrderByBuyerId", productOrderByBuyerId);
        return "orderBuyer";
    }

    @GetMapping("/orderDetailBuyer/{id}")
    public String getProductOrderDetailBuyerByBuyerId(@PathVariable Long id,ModelMap model){
        ProductOrder productOrderId = productOrderRepository.findById(id).get();
        model.addAttribute("productOrderDetailBuyer",productOrderId);
        return "orderDetailBuyer";
    }

    @GetMapping("/orderSeller")
    public String getProductOrderBySellerId(ModelMap model){
        User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ProductOrder> productOrderByProuctId = productOrderServiceImp.getProductOrderBySellerId(user.getId());
        model.addAttribute("productOrderByProductId",productOrderByProuctId);
        return "orderSeller";
    }

    @GetMapping("/orderDetailSeller/{id}")
    public String getProductOrderDetailSellerBySellerId(@PathVariable Long id,ModelMap model){
        ProductOrder productOrderId = productOrderRepository.findById(id).get();
        model.addAttribute("productOrderDetailSeller",productOrderId);
        return "orderDetailSeller";
    }

    @PostMapping("/orderDetailSeller/{id}/track")
    public String updateTrack(@PathVariable Long id, @RequestParam String track){
        Optional<ProductOrder> productOrderOptional = productOrderRepository.findById(id);

        if(productOrderOptional.isPresent()) {
            ProductOrder productOrder = productOrderOptional.get();
            productOrder.setTrack(track);
            productOrderRepository.save(productOrder);
        }
        return "redirect:/orderDetailSeller/{id}";
    }

}
