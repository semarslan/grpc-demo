package com.semarslan.grpcDemo.client;


import com.semarslan.grpcDemo.response.TransferStreamingResponse;
import com.semarslan.models.BankServiceGrpc;
import com.semarslan.models.TrabsferService;
import com.semarslan.models.TransferRequest;
import com.semarslan.models.TransferServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransferClientTest {

    private TransferServiceGrpc.TransferServiceBlockingStub blockingStub;
    private TransferServiceGrpc.TransferServiceStub stub;


    @BeforeAll
    public void setup() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();

        this.stub = TransferServiceGrpc.newStub(channel);
    }

    @Test
    public void transfer() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        TransferStreamingResponse response = new TransferStreamingResponse(latch);

        StreamObserver<TransferRequest> requestStreamObserver = this.stub.transfer(response);

        for (int i = 0; i < 100; i++) {

            TransferRequest request = TransferRequest.newBuilder()
                    .setFromAccount(ThreadLocalRandom.current().nextInt(1, 11))
                    .setToAccount(ThreadLocalRandom.current().nextInt(1, 11))
                    .setAmount(ThreadLocalRandom.current().nextInt(1, 21))
                    .build();

            requestStreamObserver.onNext(request);
        }
        requestStreamObserver.onCompleted();
        latch.await();
    }

}
