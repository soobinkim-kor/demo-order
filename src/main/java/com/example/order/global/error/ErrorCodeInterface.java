package com.example.order.global.error;

import org.springframework.http.HttpStatus;

public interface ErrorCodeInterface {
    String code();
    HttpStatus httpStatus();
    String message();
}
