package com.semarslan.grpcDemo.service;

import com.semarslan.grpcDemo.data.AccountDatabase;
import com.semarslan.models.Balance;
import com.semarslan.models.BalanceCheckRequest;
import com.semarslan.models.BankServiceGrpc;
import io.grpc.stub.StreamObserver;

public class BankService extends BankServiceGrpc.BankServiceImplBase {
    @Override
    public void getBalance(BalanceCheckRequest request, StreamObserver<Balance> responseObserver) {

        int accountNumber = request.getAccountNum();
        Balance balance = Balance.newBuilder()
                .setAmount(AccountDatabase.getBalance(accountNumber)).build();

        responseObserver.onNext(balance);
        responseObserver.onCompleted();
    }
}
