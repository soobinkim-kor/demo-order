package com.example.order.global.error;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final ErrorCodeInterface errorCode;

    public BusinessException(ErrorCodeInterface errorCode) {
        super(errorCode.message());
        this.errorCode = errorCode;
    }
}
