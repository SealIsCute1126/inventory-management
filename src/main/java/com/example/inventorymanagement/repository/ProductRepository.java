package com.example.inventorymanagement.repository;

import com.example.inventorymanagement.model.Product;
import jakarta.persistence.LockModeType; // 👈 1. 記得引入這個 (JPA 標準)
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock; // 👈 2. 還有這個 (Spring Data JPA)
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    // ⛔️ 關鍵魔法：加上這行 @Lock
    // 這會告訴資料庫：當有人讀取這筆資料時，請產生 "SELECT ... FOR UPDATE" SQL
    // 只要有一個人正在讀，其他人就必須在外面排隊，直到他改完解鎖為止。
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Product> findById(Integer id);
}