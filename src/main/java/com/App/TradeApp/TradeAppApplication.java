package com.App.TradeApp;

import com.App.TradeApp.Model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TradeAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradeAppApplication.class, args);

		User user = new User();
		user.setEmail("test@example.com");
		user.setUsername("testuser");

		System.out.println(user.getEmail());   // Should print: test@example.com
		System.out.println(user.getUsername());
	}
}
