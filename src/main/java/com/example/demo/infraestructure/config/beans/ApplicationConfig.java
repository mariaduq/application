package com.example.demo.infraestructure.config.beans;

import com.example.demo.application.mapper.PriceMapper;
import com.example.demo.application.mapper.ProductMapper;
import com.example.demo.application.mapper.UserMapper;
import com.example.demo.domain.port.ProductsPort;
import com.example.demo.domain.port.UsersPort;
import com.example.demo.application.usecases.price.GetAllPricesUseCase;
import com.example.demo.application.usecases.price.GetPricesUseCase;
import com.example.demo.application.usecases.price.GetProductPricesUseCase;
import com.example.demo.application.usecases.product.GetAllProductsUseCase;
import com.example.demo.application.usecases.user.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.domain.port.PricesPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationConfig {
	
	@Bean
	public GetPricesUseCase getPricesUseCase (PricesPort pricesPort, PriceMapper priceMapper) {
		return new GetPricesUseCase(pricesPort, priceMapper);
	}
	
	@Bean
	public GetAllPricesUseCase getAllPricesUseCase (PricesPort pricesPort, PriceMapper priceMapper) {
		return new GetAllPricesUseCase(pricesPort, priceMapper);
	}

	@Bean
	public GetProductPricesUseCase getProductPricesUseCase(PricesPort pricesPort, PriceMapper priceMapper) {
		return new GetProductPricesUseCase(pricesPort, priceMapper);
	}

	@Bean
	public SaveUserUseCase saveUserUseCase (UsersPort usersPort, UserMapper userMapper) {
		return new SaveUserUseCase(usersPort, userMapper);
	}

	@Bean
	public LoginUserUseCase loginUserUseCase (UsersPort usersPort, UserMapper userMapper) {
		return new LoginUserUseCase(usersPort, userMapper);
	}

	@Bean
	public GetUserByIdUseCase getUserByIdUseCase (UsersPort usersPort, UserMapper userMapper) {
		return new GetUserByIdUseCase(usersPort, userMapper);
	}

	@Bean
	public GetUserByEmailUseCase getUserByEmailUseCase (UsersPort usersPort, UserMapper userMapper) {
		return new GetUserByEmailUseCase(usersPort, userMapper);
	}

	@Bean
	public UpdateUserUseCase updateUserUseCase (UsersPort usersPort, UserMapper userMapper) {
		return new UpdateUserUseCase(usersPort, userMapper);
	}

	@Bean
	public DeleteUserUseCase deleteUserUseCase (UsersPort usersPort) {
		return new DeleteUserUseCase(usersPort);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(4);
	}

	@Bean
	public UpdateForgotPasswordUseCase updateForgotPasswordUseCase(UsersPort usersPort, UserMapper userMapper) {
		return new UpdateForgotPasswordUseCase(usersPort, userMapper);
	}

	@Bean
	public ChangePasswordUseCase changePasswordUseCase(UsersPort usersPort) {
		return new ChangePasswordUseCase(usersPort);
	}

	@Bean
	public GetAllProductsUseCase getAllProductsUseCase(ProductsPort productsPort, ProductMapper productMapper) {
		return new GetAllProductsUseCase(productsPort, productMapper);
	}
}
