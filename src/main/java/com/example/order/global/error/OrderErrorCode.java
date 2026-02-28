package com.example.order.global.error;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum OrderErrorCode implements ErrorCodeInterface {

    ORDER_NOT_FOUND("ORDER_001", HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다."),
    ORDER_ALREADY_CANCELLED("ORDER_002", HttpStatus.BAD_REQUEST, "이미 취소된 주문입니다."),
    ORDER_CANNOT_CANCEL("ORDER_003", HttpStatus.BAD_REQUEST, "완료된 주문은 취소할 수 없습니다."),
    USER_SERVICE_UNAVAILABLE("ORDER_004", HttpStatus.SERVICE_UNAVAILABLE, "유저 서비스를 사용할 수 없습니다."),
    USER_NOT_FOUND("ORDER_005", HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다.");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String code() { return code; }

    @Override
    public HttpStatus httpStatus() { return httpStatus; }

    @Override
    public String message() { return message; }
}
