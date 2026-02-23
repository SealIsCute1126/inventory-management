package com.example.inventorymanagement.controller;

import com.example.inventorymanagement.model.User;
import com.example.inventorymanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register") // 設定網址為 POST http://localhost:8080/users/register
    public String register(@RequestBody User user) {
        userService.register(user);
        return "註冊成功！";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        boolean isSuccess = userService.login(user.getUsername(), user.getPassword());

        if (isSuccess) {
            return "登入成功！歡迎回來 " + user.getUsername();
        } else {
            return "帳號或密碼錯誤！";
        }
    }
}