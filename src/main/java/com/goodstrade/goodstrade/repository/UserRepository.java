package com.goodstrade.goodstrade.repository;

import com.goodstrade.goodstrade.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String userName);
    User findByEmail(String email);
}
