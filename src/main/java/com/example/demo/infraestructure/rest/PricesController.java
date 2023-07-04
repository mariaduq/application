package com.example.demo.infraestructure.rest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.application.GetAllPricesUseCase;
import com.example.demo.application.GetPricesUseCase;
import com.example.demo.infraestructure.ddbb.PriceRepositoryJpa;
import com.example.demo.model.Price;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/application")
public class PricesController {
	
	@Autowired
	PriceRepositoryJpa priceRepository;
	
	@Autowired
	GetPricesUseCase getPricesUseCase;
	
	@Autowired
	GetAllPricesUseCase getAllPricesUseCase;
	
	@Operation(summary = "Get price", description = "Get the correct price of a product", operationId = "getPrice")
	@ApiResponses(value = {
	       @ApiResponse(responseCode = "200", description = "Price",
	    		   content = { @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))}),
	       @ApiResponse(responseCode = "400", description = "Bad Request",
	       		   content = { @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}),
	       @ApiResponse(responseCode = "404", description = "Not Found",
	       		   content = { @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))})
	       })
	@GetMapping("/v1/prices")
	public ResponseEntity<List<PriceDTO>> getPrice(
			@Parameter(description = "The date for which we want to know the product price",
			schema = @Schema(type = "string", format = "YYYY-MM-DD HH:mm:ss")) @RequestParam (required=false) String dateString,
			@Parameter(description = "The product for which we want to know the price") @RequestParam (required=false) Long productId,
			@Parameter(description = "The brand of the desired product") @RequestParam (required=false) Long brandId){
		
		ModelMapper mapper = new ModelMapper();
		
		if (dateString != null && productId != null && brandId != null) {
						
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
			
			Price price = getPricesUseCase.getCorrectPrice(dateTime, productId, brandId);
			PriceDTO priceDTO = mapper.map(price, PriceDTO.class);

			List<PriceDTO> list = new ArrayList<>();
			list.add(priceDTO);
			
			return ResponseEntity.ok()
					.body(list);
		}
		
		else if (dateString == null && productId == null && brandId == null) {
			
			return ResponseEntity.ok()
					.body(getAllPricesUseCase
						.findAll()
						.stream()
						.map((price) -> mapper.map(price, PriceDTO.class))
						.collect(Collectors.toList()));
		}
		
		else {
			throw new BadRequestException();
		}
	}
	
}
