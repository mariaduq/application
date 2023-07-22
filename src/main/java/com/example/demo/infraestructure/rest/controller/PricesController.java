package com.example.demo.infraestructure.rest.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.application.output.PriceOutput;
import com.example.demo.application.output.UserOutput;
import com.example.demo.application.usecases.price.GetProductPricesUseCase;
import com.example.demo.application.usecases.user.GetUserByEmailUseCase;
import com.example.demo.domain.model.User;
import com.example.demo.infraestructure.rest.exception.BadRequestException;
import com.example.demo.infraestructure.rest.dto.PriceDTO;
import com.example.demo.infraestructure.rest.mappers.UserMapper;
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
import com.example.demo.infraestructure.persistence.jpa.PriceRepositoryJpa;
import com.example.demo.domain.model.Price;

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

	@GetMapping("/productPrices/{productId}")
	public String getProductPrices(@PathVariable(name="productId")Long id, Authentication auth, Model model) throws Exception {
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
		return "No authenticated user";
	}

	@GetMapping("/form")
	public String getPriceForm(Authentication auth, Model model) throws Exception {
		if(auth != null) {
			UserDetails userDetails = (UserDetails) auth.getPrincipal();
			String email = userDetails.getUsername();

			UserOutput loggedUser = getUserByEmailUseCase.execute(email);
			model.addAttribute("user", userMapper.fromUserOutputToUserDTO(loggedUser));

			return "product-date-price-form";
		}
		return "No authenticated user";
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
	@PostMapping("/price")
	public String getPrice(
			Authentication auth,
			ModelMap model,
			@Parameter(description = "The date for which we want to know the product price",
			schema = @Schema(type = "string", format = "YYYY-MM-DD HH:mm:ss")) @Valid @ModelAttribute("date")String dateString,
			@Parameter(description = "The product for which we want to know the price") @Valid @ModelAttribute("productId")Long productId) throws Exception {

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
					PriceOutput price = getPricesUseCase.getCorrectPrice(date, productId);
					PriceDTO priceDTO = mapper.map(price, PriceDTO.class);

					String formattedDate = date.format(outputFormat);

					model.addAttribute("productPrice", priceDTO.getPrice());
					model.addAttribute("date", formattedDate);

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
		return "No authenticated user";
	}

	@GetMapping("/price")
	public String getPrice(Authentication auth, ModelMap model) throws Exception {
		if(auth != null) {
			UserDetails userDetails = (UserDetails) auth.getPrincipal();
			String email = userDetails.getUsername();

			UserOutput loggedUser = getUserByEmailUseCase.execute(email);
			model.addAttribute("user", userMapper.fromUserOutputToUserDTO(loggedUser));

			return "product-date-price";
		}
		return "No authenticated user";
	}
	
}
