package com.example.order.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "ORDER_BASE")
public class OrderEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_NO")
    private Long orderNo;

    @Column(name = "USER_ID", nullable = false)
    private String userId;

    @Column(name = "PRODUCT_NAME", nullable = false)
    private String productName;

    @Column(name = "QUANTITY", nullable = false)
    private int quantity;

    @Column(name = "PRICE", nullable = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    @Builder.Default
    private OrderStatus status = OrderStatus.PENDING;

    public void cancel() {
        if (this.status == OrderStatus.COMPLETED) {
            throw new IllegalStateException("완료된 주문은 취소할 수 없습니다.");
        }
        this.status = OrderStatus.CANCELLED;
        super.softDelete();
    }

    public void complete() {
        this.status = OrderStatus.COMPLETED;
    }
}
