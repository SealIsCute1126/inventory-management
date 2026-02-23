package com.example.inventorymanagement.controller;

import com.example.inventorymanagement.model.Product;
import com.example.inventorymanagement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 1. 取得所有商品
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // 2. 新增商品
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    // 3. 修改商品 (PUT)
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Integer id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    // 4. 刪除商品
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
    }

    // 5. 搶購商品 (API 介面)
    // 網址像是: POST /products/1/buy
    @PostMapping("/{id}/buy")
    public String buyProduct(@PathVariable Integer id) {
        try {
            // 🔄 修改這裡：原本是 buyProduct(id)，改成 purchase(id, 1)
            // 我們預設讓 API 呼叫一次就是買 1 個
            productService.purchase(id, 1);
            return "購買成功";
        } catch (Exception e) {
            return "購買失敗: " + e.getMessage();
        }
    }
}