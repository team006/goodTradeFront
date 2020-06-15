package com.goodstrade.goodstrade.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goodstrade.goodstrade.entity.Image;
import com.goodstrade.goodstrade.entity.Product;
import com.goodstrade.goodstrade.entity.ProductOrder;
import com.goodstrade.goodstrade.entity.User;
import com.goodstrade.goodstrade.repository.ImageRepository;
import com.goodstrade.goodstrade.repository.ProductOrderRepository;
import com.goodstrade.goodstrade.repository.ProductRepository;
import com.goodstrade.goodstrade.service.product.ProductServiceImp;
import com.goodstrade.goodstrade.validator.AddProductValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(Product.class);

    @Autowired
    private ProductServiceImp productServiceImp;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductOrderRepository productOrderRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AddProductValidator addProductValidator;

    @GetMapping("/")
    public String getProductAllProduct(ModelMap model){
        List<Product> allProducts = productServiceImp.getAllProducts();
        model.addAttribute("allProducts",allProducts);
        return "index";
    }

    @GetMapping("{id}")
    public String getProductDetailById(@PathVariable Long id, ModelMap model) {
        Product productId = productServiceImp.getProductDetailById(id);
        ProductOrder productOrder = new ProductOrder();
        model.addAttribute("productDetail", productId);
        model.addAttribute("productOrder", productOrder);
        return "productDetail";
    }

    @GetMapping("/myProduct")
    public String getMyProduct(ModelMap model){
        User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Product> myProduct = productServiceImp.getProductBySellerId(user.getId());
        model.addAttribute("myProduct",myProduct);
        return "myProduct";
    }

    @GetMapping("/addProduct")
    public String getaddProduct(ModelMap model){
        model.addAttribute("addProduct", new Product());
        return "addProduct";
    }

    @PostMapping("/addProduct")
    public String addProduct(@Valid Product product,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) throws ParseException, JsonProcessingException {

        User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse(product.getStartDateStr());
        Date endDate = sdf.parse(product.getEndDateStr());

        List<Long> imageIds = objectMapper.readValue(product.getImageStr(), new TypeReference<List<Long>>(){});
        List<Image> images = new ArrayList<>();

        addProductValidator.validate(product,bindingResult);
        if (bindingResult.hasErrors()){
            log.info("Product Form Error");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addProduct",bindingResult);
            redirectAttributes.addFlashAttribute("addProduct", product);
            return  "redirect:/addProduct";
        }

        for(Long imageId: imageIds) {
            Optional<Image> image = imageRepository.findById(imageId);
            if(image.isPresent()){
                images.add(image.get());
            }
        }

        Product addProduct = new Product();
        addProduct.setName(product.getName());
        addProduct.setDetail(product.getDetail());
        addProduct.setQuantity(product.getQuantity());
        addProduct.setStartDate(startDate);
        addProduct.setEndDate(endDate);
        addProduct.setSeller(user);
        addProduct.setImage(images);



        productRepository.save(addProduct);
        return "redirect:/myProduct";
    }

    @PostMapping("/productDetail/{id}/addOrder")
    public String addOrder(@Valid ProductOrder productOrder,
                           @PathVariable Long id){

        User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        Date date = new Date();
//        addProductOrder.setDate(date);

        Product product = productRepository.findProductById(id);

        ProductOrder addProductOrder = new ProductOrder();
        addProductOrder.setBuyer(user);
        addProductOrder.setSeller(product.getSeller());
        addProductOrder.setProduct(product);
        addProductOrder.setDate(new Date());
        addProductOrder.setQuantity(productOrder.getQuantity());
        addProductOrder.setDetail(productOrder.getDetail());
        productOrderRepository.save(addProductOrder);
        return "redirect:/{id}";
    }

}
