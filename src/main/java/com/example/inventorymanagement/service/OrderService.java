package com.example.inventorymanagement.service;
import com.example.inventorymanagement.dto.OrderItemRequest;
import com.example.inventorymanagement.model.*;
import com.example.inventorymanagement.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional // 重點：確保整個下單過程「要嘛全部成功，要嘛全部失敗」
    public Order placeOrder(Integer userId, List<OrderItemRequest> items) {
        // 1. 找使用者
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("使用者不存在"));

        Order order = new Order();
        order.setUser(user);
        order.setCreatedDate(new Date());

        List<OrderItem> orderItems = new ArrayList<>();
        int totalAmount = 0;

        for (OrderItemRequest itemRequest : items) {
            // 2. 找商品（這裡建議使用你之前寫的「悲觀鎖」來防止超賣！）
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("商品 ID " + itemRequest.getProductId() + " 不存在"));

            // 3. 檢查庫存
            if (product.getQuantity() < itemRequest.getQuantity()) {
                throw new RuntimeException("商品 " + product.getName() + " 庫存不足");
            }

            // 4. 扣除庫存
            product.setQuantity(product.getQuantity() - itemRequest.getQuantity());
            productRepository.save(product);

            // 5. 建立明細並紀錄「當下價格」
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPrice(product.getPrice().intValue()); // 價格快照

            orderItems.add(orderItem);
            totalAmount += product.getPrice().intValue() * itemRequest.getQuantity();
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);

        // 6. 存入訂單（因為有 Cascade，明細也會一起存進去）
        return orderRepository.save(order);
    }
}