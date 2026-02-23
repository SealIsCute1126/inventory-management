package com.example.inventorymanagement.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users") // 避免 SQL 關鍵字衝突
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) // 帳號不能重複
    private String username;

    @Column(nullable = false)
    private String password; // 這裡存的是 BCrypt 雜湊後的亂碼

    @Column(nullable = false, unique = true)
    private String email;
}