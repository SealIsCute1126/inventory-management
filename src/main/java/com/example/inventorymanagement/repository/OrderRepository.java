package com.example.inventorymanagement.repository;

import com.example.inventorymanagement.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    // 繼承 JpaRepository 就能獲得基礎的存取功能
}