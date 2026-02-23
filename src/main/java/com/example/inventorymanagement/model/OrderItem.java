package com.example.inventorymanagement.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "order_items")
@Data
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne // 多個明細對應到一張訂單
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne // 多個明細可以指向同一個商品
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Integer quantity; // 購買數量
    private Integer price;    // 重點：紀錄下單「當下」的價格，防止商品未來調價導致舊帳對不起來
}