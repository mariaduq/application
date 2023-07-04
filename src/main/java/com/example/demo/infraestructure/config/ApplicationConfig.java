package com.example.demo.infraestructure.config;

import com.example.demo.application.SaveUserUseCase;
import com.example.demo.application.ports.UsersPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.application.GetAllPricesUseCase;
import com.example.demo.application.GetPricesUseCase;
import com.example.demo.application.ports.PricesPort;

@Configuration
public class ApplicationConfig {
	
	@Bean
	public GetPricesUseCase getPricesUseCase (PricesPort pricesPort) {
		return new GetPricesUseCase(pricesPort);
	}
	
	@Bean
	public GetAllPricesUseCase getAllPricesUseCase (PricesPort pricesPort) {
		return new GetAllPricesUseCase(pricesPort);
	}

	@Bean
	public SaveUserUseCase saveUserUseCase (UsersPort usersPort) {
		return new SaveUserUseCase(usersPort);
	}

}
