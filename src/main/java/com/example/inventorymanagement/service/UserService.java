package com.example.inventorymanagement.service;

import com.example.inventorymanagement.model.User;
import com.example.inventorymanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // 注入加密工具

    public User register(User user) {
        // 1. 檢查帳號是否重複
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("帳號已存在！");
        }

        // 2. 將明文密碼加密成雜湊碼 (Hashing)
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // 3. 存入資料庫
        return userRepository.save(user);
    }

    public boolean login(String username, String rawPassword) {
        // 1. 先透過帳號找到該名使用者
        return userRepository.findByUsername(username)
                .map(user -> {
                    // 2. 關鍵：比對「明文密碼」與「資料庫裡的亂碼」
                    // matches(原始密碼, 加密後的密碼)
                    return passwordEncoder.matches(rawPassword, user.getPassword());
                })
                .orElse(false); // 找不到使用者就回傳 false
    }
}