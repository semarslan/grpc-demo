package com.semarslan.grpcDemo;

import com.semarslan.grpcDemo.server.GrpcServer;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GrpcDemoApplication {


	public static void main(String[] args) {
//		SpringApplication.run(GrpcDemoApplication.class, args);

		try {
			GrpcServer.grpcServer();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
