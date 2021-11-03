package com.semarslan.grpcDemo.server;

import com.semarslan.grpcDemo.service.BankService;
import com.semarslan.grpcDemo.service.impl.BankServiceImpl;
import com.semarslan.grpcDemo.service.impl.TransferServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {


    public static void grpcServer () throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(6565)
                .addService(new BankServiceImpl())
                .addService(new TransferServiceImpl())
                .build();

        server.start();

        server.awaitTermination();
    }
}
