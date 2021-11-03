package com.semarslan.grpcDemo;

import com.semarslan.grpcDemo.server.GrpcServer;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GrpcDemoApplication {


	static GrpcServer grpcServer;
	public static void main(String[] args) {
//		SpringApplication.run(GrpcDemoApplication.class, args);

		try {
			grpcServer.grpcServer();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
