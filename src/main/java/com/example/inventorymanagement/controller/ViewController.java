package com.example.inventorymanagement.controller;

import com.example.inventorymanagement.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
@Controller // 注意：這裡是用 @Controller，不是 @RestController
public class ViewController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
    public String index(Model model) {
        // 把資料庫所有商品抓出來，傳給 HTML
        model.addAttribute("products", productRepository.findAll());
        return "inventory"; // 對應到 inventory.html
    }
}