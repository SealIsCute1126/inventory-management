package com.example.inventorymanagement.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders") // 資料表名稱
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne // 多張訂單對應到一位使用者
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 紀錄是誰下的單

    @Column(name = "total_amount")
    private Integer totalAmount; // 訂單總金額

    @Column(name = "created_date")
    private Date createdDate; // 下單時間

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems; // 這張訂單包含的所有商品明細
}