package com.example.inventorymanagement.service;

import com.example.inventorymanagement.model.Product;
import com.example.inventorymanagement.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 👈 記得引入這個

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // --- 基本 CRUD 功能 ---

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // 功能 C: 修改商品 (Update)
    // ✅ 修正：加上 @Transactional，這樣才能使用有上鎖的 findById
    @Transactional
    public Product updateProduct(Integer id, Product newProductData) {
        Product existingProduct = productRepository.findById(id).orElse(null);

        if (existingProduct != null) {
            existingProduct.setName(newProductData.getName());
            existingProduct.setPrice(newProductData.getPrice());
            existingProduct.setQuantity(newProductData.getQuantity());
            return productRepository.save(existingProduct);
        }
        return null;
    }

    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    // --- 搶購核心功能 (修正版) ---

    // ✅ 重點 1：加上 @Transactional，確保「鎖」會一直持續到整個方法執行完畢才釋放
    @Transactional
    public void purchase(Integer id, int quantity) {
        // ✅ 重點 2：這裡呼叫 findById 時，因為 Repository 有加 @Lock，所以會鎖住這行資料
        // 其他執行緒會在這裡排隊等待
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("找不到商品 ID: " + id));

        // 檢查庫存
        if (product.getQuantity() < quantity) {
            System.out.println("庫存不足，購買失敗 (剩餘: " + product.getQuantity() + ")");
            return; // 或是 throw new RuntimeException("庫存不足");
        }

        // 扣庫存
        product.setQuantity(product.getQuantity() - quantity);

        // 存檔
        productRepository.save(product);

        // 方法結束 -> 交易提交 (Commit) -> 鎖釋放 (Unlock) -> 下一個人進來
    }
}