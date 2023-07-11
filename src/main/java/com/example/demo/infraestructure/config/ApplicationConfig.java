package com.example.demo.infraestructure.config;

import com.example.demo.application.*;
import com.example.demo.application.mapper.UserMapper;
import com.example.demo.application.ports.UsersPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.application.ports.PricesPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
	public SaveUserUseCase saveUserUseCase (UsersPort usersPort, UserMapper userMapper) {
		return new SaveUserUseCase(usersPort, userMapper);
	}

	@Bean
	public LoginUserUseCase loginUserUseCase (UsersPort usersPort, UserMapper userMapper) {
		return new LoginUserUseCase(usersPort, userMapper);
	}

	@Bean
	public GetUserByIdUseCase getUserByIdUseCase (UsersPort usersPort) {
		return new GetUserByIdUseCase(usersPort);
	}

	@Bean
	public GetUserByEmailUseCase getUserByEmailUseCase (UsersPort usersPort) {
		return new GetUserByEmailUseCase(usersPort);
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

}
