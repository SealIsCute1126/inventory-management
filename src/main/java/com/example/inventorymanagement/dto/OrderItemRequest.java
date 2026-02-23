package com.example.inventorymanagement.dto;

import lombok.Data;

@Data // 自動幫你寫好 Getter, Setter, toString
public class OrderItemRequest {
    private Integer productId;
    private Integer quantity;
}