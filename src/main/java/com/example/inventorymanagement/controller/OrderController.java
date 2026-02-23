package com.example.inventorymanagement.controller;

import com.example.inventorymanagement.dto.OrderItemRequest;
import com.example.inventorymanagement.model.Order;
import com.example.inventorymanagement.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order placeOrder(@RequestParam Integer userId,
                            @RequestBody List<OrderItemRequest> items) {
        // 呼叫 Service 執行複雜的「扣庫存 + 存訂單」邏輯
        return orderService.placeOrder(userId, items);
    }
}