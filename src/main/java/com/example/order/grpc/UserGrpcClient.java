package com.example.order.grpc;

import com.example.order.global.error.BusinessException;
import com.example.order.global.error.OrderErrorCode;
import com.soobin.user.grpc.UserRequest;
import com.soobin.user.grpc.UserResponse;
import com.soobin.user.grpc.UserServiceGrpc;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserGrpcClient {

    // application.yml의 grpc.client.user-service 설정과 매핑
    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub userServiceStub;

    public UserResponse getUser(String userId) {
        try {
            UserRequest request = UserRequest.newBuilder()
                    .setUserId(userId)
                    .build();
            return userServiceStub.getUser(request);
        } catch (StatusRuntimeException e) {
            log.error("gRPC 호출 실패 - userId: {}, status: {}", userId, e.getStatus(), e);
            throw new BusinessException(OrderErrorCode.USER_SERVICE_UNAVAILABLE);
        }
    }
}
