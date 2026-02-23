package com.example.inventorymanagement.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private int status;      // HTTP 狀態碼
    private String message;  // 給前端看的錯誤訊息
}