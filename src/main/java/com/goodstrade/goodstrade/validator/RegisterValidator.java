package com.goodstrade.goodstrade.validator;

import com.goodstrade.goodstrade.entity.User;
import com.goodstrade.goodstrade.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RegisterValidator implements Validator {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        User checkusername = userRepository.findByUsername(user.getUsername());
        if (checkusername != null){
            errors.rejectValue("username",null , "username นี้มีผู้ใช้งานแล้ว");
        }

        // Check Email is not registered.
        User checkemail = userRepository.findByEmail(user.getEmail());
        if (checkemail != null) {
            errors.rejectValue("email", null, "email นี้มีผู้ใช้งานแล้ว");
        }
    }


}