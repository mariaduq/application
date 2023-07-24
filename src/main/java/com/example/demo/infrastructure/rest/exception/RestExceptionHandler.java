package com.example.demo.infrastructure.rest.exception;

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
				.body("No hay resultados para tu consulta.");
				
	}
	
	@ExceptionHandler(MappingException.class)
	protected ResponseEntity<String> handleMappingException(MappingException exc){
		
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body("La fecha debe estar en formato JDBC [yyyy-MM-dd HH:mm:ss.fffffffff].");
				
	}
	
	@ExceptionHandler(DateTimeParseException.class)
	protected ResponseEntity<String> handleDateTimeParseException(DateTimeParseException exc){
		
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body("La fecha debe estar en formato [yyyy-MM-dd HH:mm:ss].");
				
	}
	
	@ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleAuthenticationException(BadRequestException ex) {
        
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body("Número inválido de parámetros. Prueba otra vez.");
    }

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception e) {
		String errorMessage = "Se ha producido un error: " + e.getMessage();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
	}
}
