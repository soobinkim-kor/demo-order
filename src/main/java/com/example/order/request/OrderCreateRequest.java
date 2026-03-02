package com.example.order.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class OrderCreateRequest {

    @NotBlank(message = "userId는 필수입니다.")
    private String userId;

    @NotBlank(message = "상품명은 필수입니다.")
    private String productName;

    @Min(value = 1, message = "수량은 1 이상이어야 합니다.")
    private int quantity;

    @NotNull(message = "가격은 필수입니다.")
    private BigDecimal price;
}
