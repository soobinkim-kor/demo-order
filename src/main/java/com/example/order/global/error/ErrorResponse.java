package com.example.order.global.error;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {
    private final String code;
    private final String message;
    private final String path;
    private final LocalDateTime timestamp;

    public static ErrorResponse of(ErrorCodeInterface errorCode, String path) {
        return ErrorResponse.builder()
                .code(errorCode.code())
                .message(errorCode.message())
                .path(path)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
