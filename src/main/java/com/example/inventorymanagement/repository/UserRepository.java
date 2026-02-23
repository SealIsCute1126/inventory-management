package com.example.inventorymanagement.repository;

import com.example.inventorymanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    // 讓 Spring Data JPA 自動幫我們產生 SELECT * FROM users WHERE username = ?
    Optional<User> findByUsername(String username);
}