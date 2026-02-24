package com.example.demo.service;

import com.example.demo.entity.UserEntity;
import com.example.demo.repository.user.UserRepository;
import com.soobin.user.grpc.UserRequest;
import com.soobin.user.grpc.UserResponse;
import com.soobin.user.grpc.UserServiceGrpc;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class UserGrpcService extends UserServiceGrpc.UserServiceImplBase {

    private final UserRepository userRepository;

    @Override
    public void getUser(UserRequest request, StreamObserver<UserResponse> responseObserver) {
        log.debug("gRPC getUser 요청 - userId: {}", request.getUserId());

        userRepository.findByUsrId(request.getUserId())
                .map(this::toResponse)
                .ifPresentOrElse(
                        response -> {
                            responseObserver.onNext(response);
                            responseObserver.onCompleted();
                        },
                        () -> responseObserver.onError(
                                Status.NOT_FOUND
                                        .withDescription("User not found: " + request.getUserId())
                                        .asRuntimeException()
                        )
                );
    }

    private UserResponse toResponse(UserEntity user) {
        return UserResponse.newBuilder()
                .setUserId(user.getUsrId())
                .setName(user.getUsrNm())
                .setEmail(user.getUsrEmail() != null ? user.getUsrEmail() : "")
                .build();
    }
}
