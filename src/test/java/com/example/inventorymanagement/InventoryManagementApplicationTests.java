package com.example.inventorymanagement;

import com.example.inventorymanagement.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 使用真實資料庫
class InventoryManagementApplicationTests {

    @Autowired
    private ProductService productService;

    @Test
    void testConcurrentPurchase() throws InterruptedException {
        // 1. 設定參數
        int numberOfThreads = 1000; // 1000 人搶購

        // 2. 建立執行緒池 (模擬併發)
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        System.out.println("--- 搶購開始 ---");

        // 3. 發動攻擊
        for (int i = 0; i < numberOfThreads; i++) {
            executorService.execute(() -> {
                try {
                    // 🔄 修正這裡：改成呼叫新的 purchase 方法 (ID=1, 買 1 個)
                    productService.purchase(1, 1);
                } catch (Exception e) {
                    // 這裡可能會印出「庫存不足」，是正常的
                    // System.out.println(e.getMessage());
                } finally {
                    latch.countDown(); // 報數，代表我跑完了
                }
            });
        }

        // 4. 等待所有人都跑完 (這行很重要！)
        latch.await();

        System.out.println("--- 搶購結束 ---");
    }
}