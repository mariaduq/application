package com.example.demo.infrastructure.rest.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.example.demo.application.output.PriceOutput;
import com.example.demo.application.output.UserOutput;
import com.example.demo.application.usecases.price.GetProductPricesUseCase;
import com.example.demo.application.usecases.user.GetUserByEmailUseCase;
import com.example.demo.infrastructure.rest.exception.BadRequestException;
import com.example.demo.infrastructure.rest.dto.PriceDTO;
import com.example.demo.infrastructure.rest.mappers.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.example.demo.application.usecases.price.GetAllPricesUseCase;
import com.example.demo.application.usecases.price.GetPricesUseCase;
import com.example.demo.infrastructure.persistence.jpa.PriceRepositoryJpa;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Controller
@RequiredArgsConstructor
@RequestMapping("/application/v1/prices")
public class PricesController {
	
	@Autowired
	PriceRepositoryJpa priceRepository;
	
	@Autowired
	GetPricesUseCase getPricesUseCase;
	
	@Autowired
	GetAllPricesUseCase getAllPricesUseCase;

	@Autowired
	private final GetProductPricesUseCase getProductPricesUseCase;

	@Autowired
	private GetUserByEmailUseCase getUserByEmailUseCase;

	private final UserMapper userMapper;

	@Operation(summary = "Get product prices", description = "Get all the possible prices for a given product according to date", operationId = "getProductPrices")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success",
					content = { @Content(mediaType = "text/html") }),
			@ApiResponse(responseCode = "401", description = "Unauthorized",
					content = { @Content(mediaType = "text/html", schema = @Schema(implementation = String.class))}),
			@ApiResponse(responseCode = "404", description = "Not Found",
					content = { @Content(mediaType = "text/html", schema = @Schema(implementation = String.class))})
	})
	@GetMapping("/productPrices/{productId}")
	public String getProductPrices(@Parameter(description = "The ID of the product I want to know the prices for", schema = @Schema(type = "integer", format = "int64"), example = "9136275")
									   @PathVariable(name="productId")Long id, Authentication auth, Model model) throws Exception {
		if(auth != null) {
			UserDetails userDetails = (UserDetails) auth.getPrincipal();
			String email = userDetails.getUsername();

			UserOutput loggedUser = getUserByEmailUseCase.execute(email);
			model.addAttribute("user", userMapper.fromUserOutputToUserDTO(loggedUser));

			ModelMapper mapper = new ModelMapper();

			try{
				model.addAttribute("productPricesList", getProductPricesUseCase.execute(id));
			} catch (Exception e) {
				model.addAttribute("formErrorMessage", e.getMessage());
			}
			return "product-prices-list";
		}
		else{
			model.addAttribute("errorMessage", "Error: usuario sin autentificar.");
			model.addAttribute("errorDescription", "Para poder acceder a esta página es necesario que el " +
					"usuario se haya identificado previamente en la aplicación");
			return "error";
		}
	}

	@Operation(summary = "Get price form", description = "Get the form to find the price of a product on a given date for the authenticated user", operationId = "getPriceForm")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success",
					content = { @Content(mediaType = "text/html") }),
			@ApiResponse(responseCode = "401", description = "Unauthorized",
					content = { @Content(mediaType = "text/html", schema = @Schema(implementation = String.class))})
	})
	@GetMapping("/form")
	public String getPriceForm(Authentication auth, Model model) throws Exception {
		if(auth != null) {
			UserDetails userDetails = (UserDetails) auth.getPrincipal();
			String email = userDetails.getUsername();

			UserOutput loggedUser = getUserByEmailUseCase.execute(email);
			model.addAttribute("user", userMapper.fromUserOutputToUserDTO(loggedUser));

			return "product-date-price-form";
		}
		else{
			model.addAttribute("errorMessage", "Error: usuario sin autentificar.");
			model.addAttribute("errorDescription", "Para poder acceder a esta página es necesario que el " +
					"usuario se haya identificado previamente en la aplicación");
			return "error";
		}
	}

	@Operation(summary = "Get price", description = "Get the correct price of a product for the authenticated user", operationId = "getPrice")
	@ApiResponses(value = {
	       @ApiResponse(responseCode = "200", description = "Success",
	    		   content = { @Content(mediaType = "text/html")}),
	       @ApiResponse(responseCode = "400", description = "Bad Request",
	       		   content = { @Content(mediaType = "text/html", schema = @Schema(implementation = String.class))}),
		   @ApiResponse(responseCode = "401", description = "Unauthorized",
				   content = { @Content(mediaType = "text/html", schema = @Schema(implementation = String.class))}),
	       @ApiResponse(responseCode = "404", description = "Not Found",
	       		   content = { @Content(mediaType = "text/html", schema = @Schema(implementation = String.class))})
	       })
	@PostMapping("/price")
	public String getPrice(
			Authentication auth,
			ModelMap model,
			@Parameter(description = "The date for which we want to know the product price",
				schema = @Schema(type = "string", format = "yyyy-MM-dd'T'HH:mm"), example = "2024-12-09'T'19:54")
			@Valid @RequestParam("date")String dateString,
			@Parameter(description = "The id of the product for which we want to know the price", schema = @Schema(type = "integer", format = "int64"), example = "9136275")
			@Valid @RequestParam("productId")Long productId) throws Exception {

		if(auth != null) {
			UserDetails userDetails = (UserDetails) auth.getPrincipal();
			String email = userDetails.getUsername();

			UserOutput loggedUser = getUserByEmailUseCase.execute(email);
			model.addAttribute("user", userMapper.fromUserOutputToUserDTO(loggedUser));

			ModelMapper mapper = new ModelMapper();

			if (dateString != null && productId != null) {
				DateTimeFormatter entryFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
				DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");

				try {
					LocalDateTime date = LocalDateTime.parse(dateString, entryFormat);
					PriceOutput price = getPricesUseCase.execute(date, productId);
					PriceDTO priceDTO = mapper.map(price, PriceDTO.class);

					String formattedDate = date.format(outputFormat);

					model.addAttribute("productPrice", priceDTO.getPrice());
					model.addAttribute("date", formattedDate);
					model.addAttribute("productId", productId);

					return getPrice(auth, model);

				} catch (Exception e) {
					model.addAttribute("formErrorMessage", e.getMessage());
				}
			}
			else {
				throw new BadRequestException();
			}
			return "product-date-price-form";
		}
		else{
			model.addAttribute("errorMessage", "Error: usuario sin autentificar.");
			model.addAttribute("errorDescription", "Para poder acceder a esta página es necesario que el " +
					"usuario se haya identificado previamente en la aplicación");
			return "error";
		}
	}

	@Operation(summary = "Get price", description = "Get the correct price of a product for the authenticated user", operationId = "getPrice")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success",
					content = { @Content(mediaType = "text/html")}),
			@ApiResponse(responseCode = "401", description = "Unauthorized",
					content = { @Content(mediaType = "text/html", schema = @Schema(implementation = String.class))}),
			@ApiResponse(responseCode = "404", description = "Not Found",
					content = { @Content(mediaType = "text/html", schema = @Schema(implementation = String.class))})
	})
	@GetMapping("/price")
	public String getPrice(Authentication auth, ModelMap model) throws Exception {
		if(auth != null) {
			UserDetails userDetails = (UserDetails) auth.getPrincipal();
			String email = userDetails.getUsername();

			UserOutput loggedUser = getUserByEmailUseCase.execute(email);
			model.addAttribute("user", userMapper.fromUserOutputToUserDTO(loggedUser));

			return "product-date-price";
		}
		else{
			model.addAttribute("errorMessage", "Error: usuario sin autentificar.");
			model.addAttribute("errorDescription", "Para poder acceder a esta página es necesario que el " +
					"usuario se haya identificado previamente en la aplicación");
			return "error";
		}
	}
	
}
