package com.semarslan.grpcDemo.service.impl;

import com.semarslan.grpcDemo.request.TransferStreamingRequest;
import com.semarslan.models.TransferRequest;
import com.semarslan.models.TransferResponse;
import com.semarslan.models.TransferServiceGrpc;
import io.grpc.stub.StreamObserver;

public class TransferServiceImpl extends TransferServiceGrpc.TransferServiceImplBase {

    @Override
    public StreamObserver<TransferRequest> transfer(StreamObserver<TransferResponse> responseObserver) {
        return new TransferStreamingRequest(responseObserver);
    }
}
