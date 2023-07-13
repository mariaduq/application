package com.example.demo.infraestructure.rest.exception;

import java.time.format.DateTimeParseException;

import org.modelmapper.MappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<String> handleExceptionEntityNotFound(EntityNotFoundException exc){
		
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body("There are no results for your request. Try again.");
				
	}
	
	@ExceptionHandler(MappingException.class)
	protected ResponseEntity<String> handleMappingException(MappingException exc){
		
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body("Date must be in JDBC format [yyyy-MM-dd HH:mm:ss.fffffffff]. Try again.");
				
	}
	
	@ExceptionHandler(DateTimeParseException.class)
	protected ResponseEntity<String> handleDateTimeParseException(DateTimeParseException exc){
		
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body("Date must be in format [yyyy-MM-dd HH:mm:ss]. Try again.");
				
	}
	
	@ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleAuthenticationException(BadRequestException ex) {
        
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body("Invalid number of parameters. Try again.");
    }
}
