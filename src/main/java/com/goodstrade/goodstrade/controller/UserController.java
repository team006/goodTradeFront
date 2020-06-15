package com.goodstrade.goodstrade.controller;

import com.goodstrade.goodstrade.entity.Role;
import com.goodstrade.goodstrade.entity.User;
import com.goodstrade.goodstrade.validator.RegisterValidator;
import com.goodstrade.goodstrade.repository.UserRepository;
import com.goodstrade.goodstrade.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UserController {

    private static final String ADMIN_TEST_USER_NAME = "admin1";
    private static final String ADMIN_TEST_PASSWORD = "admin1";
    private static final String SELLER_TEST_USER_NAME = "seller1";
    private static final String SELLER_TEST_PASSWORD = "seller1";
    private static final String TEST_USER_NAME = "user1";
    private static final String TEST_PASSWORD = "user1";
    private static final Logger log = LoggerFactory.getLogger(User.class);

    @Autowired
    private RegisterValidator registerValidator;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/createadmin1")
    public String createTestAdmin1() {
        User user = userRepository.findByUsername("admin1");
        if(user != null) {
            return "User is created";
        }

        user = new User();
        user.setUsername(ADMIN_TEST_USER_NAME);
        user.setPassword(ADMIN_TEST_PASSWORD);
        user.setRole(Role.ROLE_ADMIN);
        user.setName("a");
        user.setAddress("ads");
        user.setEmail("ass");
        user.setTel("0123");
        userRepository.save(user);

        return "Create test user";
    }

    @GetMapping("/createuser1")
    public String createTestUser1() {
        User user = userRepository.findByUsername("user1");
        if(user != null) {
            return "User is created";
        }

        user = new User();
        user.setUsername(TEST_USER_NAME);
        user.setPassword(TEST_PASSWORD);
        user.setRole(Role.ROLE_CLIENT);
        user.setName("b");
        user.setAddress("ads");
        user.setEmail("ass");
        user.setTel("0123");
        userRepository.save(user);

        return "Create test user";
    }

    @GetMapping("/createseller1")
    public String createTestSeller1() {
        User user = userRepository.findByUsername("seller1");
        if(user != null) {
            return "User is created";
        }

        user = new User();
        user.setUsername(SELLER_TEST_USER_NAME);
        user.setPassword(SELLER_TEST_PASSWORD);
        user.setRole(Role.ROLE_SHOP);
        user.setName("c");
        user.setAddress("ads");
        user.setEmail("ass");
        user.setTel("0123");
        userRepository.save(user);

        return "Create test user";
    }

    @GetMapping("/adminonly")
    public String adminOnly() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "This is ADMIN " + user.getUsername();
    }

    @GetMapping("/logedinOnly")
    public String logedInOnly() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "This is " + user.getUsername();
    }

    @GetMapping("/guestonly")
    public String guestOnly() {
        return "Only Guest Here";
    }

    @GetMapping("/all")
    public String all() {
        return "AnyOne Can Come here";
    }


    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String registration(ModelMap model){
        if(!model.containsAttribute("createUser")) {
            model.addAttribute("createUser", new User());
        }
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid User user,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){

        registerValidator.validate(user,bindingResult);
        if (bindingResult.hasErrors()){
            log.info("User Form Error");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.createUser",bindingResult);
            redirectAttributes.addFlashAttribute("createUser", user);
            return  "redirect:/register";
        }

        user.setRole(Role.ROLE_CLIENT);
        userRepository.save(user);
        return "redirect:/login";
    }

//    @PostMapping("/register")
//    String register(@Valid @ModelAttribute("createUser") User createUser, BindingResult result, ModelMap model) {
//        log.info(createUser.toString());
//        if (result.hasErrors()) {
//            return "index";
//        }
//
//        registerValidator.validate(createUser, result);
//
//        if (result.hasErrors()) {
//            List<ObjectError> allErrors = result.getAllErrors();
//            for (ObjectError objectError : allErrors) {
//                log.error("\t*** " + objectError);
//            }
//            return "index";
//        } else {
//            userService.save(createUser);
//            return "redirect:login";
//        }
//    }

}
