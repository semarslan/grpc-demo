package com.semarslan.grpcDemo.client;


import com.semarslan.models.Balance;
import com.semarslan.models.BalanceCheckRequest;
import com.semarslan.models.BankServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BankClientTest {

    private BankServiceGrpc.BankServiceBlockingStub blockingStub;

    @BeforeAll
    public void setup() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();

        this.blockingStub = BankServiceGrpc.newBlockingStub(channel);
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
}
