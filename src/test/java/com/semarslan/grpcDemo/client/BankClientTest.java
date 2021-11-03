package com.semarslan.grpcDemo.client;


import com.semarslan.grpcDemo.response.BalanceStreamObserver;
import com.semarslan.grpcDemo.response.MoneyStreamingResponse;
import com.semarslan.models.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.CountDownLatch;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BankClientTest {

    private BankServiceGrpc.BankServiceBlockingStub blockingStub;
    private BankServiceGrpc.BankServiceStub bankServiceStub;

    @BeforeAll
    public void setup() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();

        this.blockingStub = BankServiceGrpc.newBlockingStub(channel);
        this.bankServiceStub = BankServiceGrpc.newStub(channel);
    }

    @Test
    public void balanceTest() {

        BalanceCheckRequest request = BalanceCheckRequest
                .newBuilder()
                .setAccountNum(5)
                .build();

        Balance balance = this.blockingStub.getBalance(request);
        System.out.println(
                "Received: " + balance.getAmount()
        );
    }

    @Test
    public void withDrawTest(){
        WithdrawRequest request = WithdrawRequest.newBuilder()
                .setAccountNum(7).setAmount(40).build();
        this.blockingStub.withdraw(request).forEachRemaining(money -> System.out.println(
                "Received: " + money.getValue()
        ));
    }

    @Test
    public void withDrawAsyncTest() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        WithdrawRequest request = WithdrawRequest.newBuilder()
                .setAccountNum(10).setAmount(50).build();
        this.bankServiceStub.withdraw(request, new MoneyStreamingResponse(latch));
        latch.await();

    }

    @Test
    public void cashStreamingRequest() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        StreamObserver<DepositRequest> streamObserver = this.bankServiceStub.cashDeposit(new BalanceStreamObserver(latch));
        for (int i = 0; i < 10; i++) {
            DepositRequest request = DepositRequest.newBuilder().setAccountNum(8).setAmount(10).build();
            streamObserver.onNext(request);
        }

        streamObserver.onCompleted();
        latch.await();
    }
}
