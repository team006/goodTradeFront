package com.goodstrade.goodstrade.service.user;

import com.goodstrade.goodstrade.entity.Role;
import com.goodstrade.goodstrade.entity.User;
import com.goodstrade.goodstrade.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registration(User user){

        User register = new User();

        register.setUsername(user.getUsername());
        register.setPassword(user.getPassword());
        register.setName(user.getName());
        register.setAddress(user.getAddress());
        register.setEmail(user.getEmail());
        register.setTel(user.getTel());
        register.setRole(Role.ROLE_CLIENT);

        return userRepository.save(user);
    }


}
