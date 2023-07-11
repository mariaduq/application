package com.example.demo.infraestructure.rest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.example.demo.application.GetProductPricesUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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

@Controller
@RequiredArgsConstructor
@RequestMapping("/application")
public class PricesController {
	
	@Autowired
	PriceRepositoryJpa priceRepository;
	
	@Autowired
	GetPricesUseCase getPricesUseCase;
	
	@Autowired
	GetAllPricesUseCase getAllPricesUseCase;

	@Autowired
	private final GetProductPricesUseCase getProductPricesUseCase;

	@GetMapping("/productPrices/{productId}")
	public String getProductPrices(@PathVariable(name="productId")Long id, Model model) {
		model.addAttribute("productPricesList", getProductPricesUseCase.execute(id));
		return "product-prices-list";
	}

	@GetMapping("/datePrice")
	public String getPriceForm() {
		return "product-date-price-form";
	}

	@Operation(summary = "Get price", description = "Get the correct price of a product", operationId = "getPrice")
	@ApiResponses(value = {
	       @ApiResponse(responseCode = "200", description = "Price",
	    		   content = { @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))}),
	       @ApiResponse(responseCode = "400", description = "Bad Request",
	       		   content = { @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}),
	       @ApiResponse(responseCode = "404", description = "Not Found",
	       		   content = { @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))})
	       })
	@PostMapping("/v1/prices")
	public String getPrice(
			ModelMap model,
			@Parameter(description = "The date for which we want to know the product price",
			schema = @Schema(type = "string", format = "YYYY-MM-DD HH:mm:ss")) @Valid @ModelAttribute("date")String dateString,
			@Parameter(description = "The product for which we want to know the price") @Valid @ModelAttribute("productId")Long productId){
		
		ModelMapper mapper = new ModelMapper();
		
		if (dateString != null && productId != null) {
			DateTimeFormatter entryFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

			try {
				LocalDateTime date = LocalDateTime.parse(dateString, entryFormat);
				Price price = getPricesUseCase.getCorrectPrice(date, productId);
				PriceDTO priceDTO = mapper.map(price, PriceDTO.class);

				model.addAttribute("productPrice", priceDTO.getPrice()+" EUR");

				return getPrice(model);

			} catch (Exception e) {
				System.out.println("Error al parsear la fecha: " + e.getMessage());
			}
		}
		else {
			throw new BadRequestException();
		}
		return "product-date-price-form";
	}

	@GetMapping("/price")
	public String getPrice(ModelMap model) {
		return "product-date-price";
	}
	
}
