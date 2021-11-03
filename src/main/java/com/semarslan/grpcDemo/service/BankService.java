package com.semarslan.grpcDemo.service;

import com.semarslan.models.Balance;
import com.semarslan.models.BalanceCheckRequest;
import com.semarslan.models.Money;
import com.semarslan.models.WithdrawRequest;
import io.grpc.stub.StreamObserver;

public interface BankService {
    public void getBalance(BalanceCheckRequest request, StreamObserver<Balance> responseObserver);

    public void withdraw(WithdrawRequest request, StreamObserver<Money> responseObserver);
}
