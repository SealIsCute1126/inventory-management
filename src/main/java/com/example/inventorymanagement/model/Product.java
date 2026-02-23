package com.example.inventorymanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // 告訴 Spring Boot 這個class(Product)是一個資料庫的Table
@Data   // Lombok 幫我們自動生成 Getter, Setter, toString 等方法 (省去手寫)
@NoArgsConstructor // 生成無參數建構子 (JPA 需要)
@AllArgsConstructor // 生成全參數建構子 (方便我們測試)
@Table(name = "products") // 指定資料庫裡的資料表名稱叫做 "products"
public class Product {

    @Id // 這是主鍵 (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID 自動跳號 (1, 2, 3...)
    private Integer id;

    private String name;

    private Double price;

    private Integer quantity;
}
