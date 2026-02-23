package com.example.inventorymanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // 讓這個類別變成全域的攔截器
public class GlobalExceptionHandler {

    // 專門攔截我們在 OrderService 噴出的 RuntimeException
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {

        // 封裝錯誤訊息
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(), // 改成回傳 400 (Bad Request)
                ex.getMessage()                 // 這會抓到你寫的 "庫存不足"
        );

        // 回傳 ResponseEntity，這會決定前端收到的 Status Code
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}