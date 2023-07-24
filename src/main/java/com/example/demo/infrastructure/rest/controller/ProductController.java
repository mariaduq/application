package com.example.demo.infrastructure.rest.controller;

import com.example.demo.application.output.UserOutput;
import com.example.demo.application.usecases.product.GetAllProductsUseCase;
import com.example.demo.application.usecases.product.GetProductsByBrandUseCase;
import com.example.demo.application.usecases.product.GetProductsByTypeUseCase;
import com.example.demo.application.usecases.user.GetUserByEmailUseCase;
import com.example.demo.infrastructure.rest.mappers.ProductMapper;
import com.example.demo.infrastructure.rest.mappers.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/application/v1/products")
public class ProductController {

    @Autowired
    private final GetAllProductsUseCase getAllProductsUseCase;

    @Autowired
    private final GetProductsByBrandUseCase getProductsByBrandUseCase;

    @Autowired
    private final GetProductsByTypeUseCase getProductsByTypeUseCase;

    @Autowired
    private GetUserByEmailUseCase getUserByEmailUseCase;

    private final UserMapper userMapper;

    private final ProductMapper productMapper;

    @Operation(summary = "Get products list", description = "Get a list with all of the existing products", operationId = "getProductsList")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = { @Content(mediaType = "text/html")}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "text/html", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = { @Content(mediaType = "text/html", schema = @Schema(implementation = String.class))})
    })
    @GetMapping("/list")
    public String getProductsList(Authentication auth, Model model) throws Exception {
        if(auth != null) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String email = userDetails.getUsername();

            UserOutput loggedUser = getUserByEmailUseCase.execute(email);
            model.addAttribute("user", userMapper.fromUserOutputToUserDTO(loggedUser));

            try{
                model.addAttribute("productsList", getAllProductsUseCase.execute()
                        .stream()
                        .map(productMapper::fromProductOutputToProductDTO)
                        .collect(Collectors.toList()));
            } catch (Exception e) {
                model.addAttribute("formErrorMessage", e.getMessage());
            }
            return "products-list";
        }
        else{
            model.addAttribute("errorMessage", "Error: usuario sin autentificar.");
            model.addAttribute("errorDescription", "Para poder acceder a esta página es necesario que el " +
                    "usuario se haya identificado previamente en la aplicación");
            return "error";
        }
    }

    @Operation(summary = "Get product brand form", description = "Get the form to find all the products of a specific brand", operationId = "getProductsByBrandForm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = { @Content(mediaType = "text/html")}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "text/html", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = { @Content(mediaType = "text/html", schema = @Schema(implementation = String.class))})
    })
    @GetMapping("/brandForm")
    public String getProductsByBrandForm(Authentication auth, Model model) throws Exception {
        if(auth != null) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String email = userDetails.getUsername();

            UserOutput loggedUser = getUserByEmailUseCase.execute(email);
            model.addAttribute("user", userMapper.fromUserOutputToUserDTO(loggedUser));

            return "brand-product-form";
        }
        else{
            model.addAttribute("errorMessage", "Error: usuario sin autentificar.");
            model.addAttribute("errorDescription", "Para poder acceder a esta página es necesario que el " +
                    "usuario se haya identificado previamente en la aplicación");
            return "error";
        }
    }

    @Operation(summary = "Get brand products", description = "Get all the products from a specific brand", operationId = "getProductsByBrand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = { @Content(mediaType = "text/html")}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "text/html", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = { @Content(mediaType = "text/html", schema = @Schema(implementation = String.class))})
    })
    @PostMapping("/brandProducts")
    public String getProductsByBrand(Authentication auth, ModelMap model,
             @Parameter(description = "The name of the brand we wish to see the products of",
             schema = @Schema(type = "string"), example = "Bosch") @Valid @RequestParam("brandName")String brandName) throws Exception {
        if(auth != null) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String email = userDetails.getUsername();

            UserOutput loggedUser = getUserByEmailUseCase.execute(email);
            model.addAttribute("user", userMapper.fromUserOutputToUserDTO(loggedUser));

            try{
                model.addAttribute("productsBrandList", getProductsByBrandUseCase.execute(brandName)
                        .stream()
                        .map(productMapper::fromProductOutputToProductDTO)
                        .collect(Collectors.toList()));

                return getProductsByBrand(auth, model);

            } catch (Exception e) {
                model.addAttribute("formErrorMessage", e.getMessage());
            }
            return "brand-product-form";
        }
        else{
            model.addAttribute("errorMessage", "Error: usuario sin autentificar.");
            model.addAttribute("errorDescription", "Para poder acceder a esta página es necesario que el " +
                    "usuario se haya identificado previamente en la aplicación");
            return "error";
        }
    }

    @Operation(summary = "Get brand products", description = "Show all the products from a specific brand in a formatted table", operationId = "getProductsByBrand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = { @Content(mediaType = "text/html")}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "text/html", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = { @Content(mediaType = "text/html", schema = @Schema(implementation = String.class))})
    })
    @GetMapping("/brandProducts")
    public String getProductsByBrand(Authentication auth, ModelMap model) throws Exception {
        if(auth != null) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String email = userDetails.getUsername();

            UserOutput loggedUser = getUserByEmailUseCase.execute(email);
            model.addAttribute("user", userMapper.fromUserOutputToUserDTO(loggedUser));

            return "products-by-brand-list";
        }
        else{
            model.addAttribute("errorMessage", "Error: usuario sin autentificar.");
            model.addAttribute("errorDescription", "Para poder acceder a esta página es necesario que el " +
                    "usuario se haya identificado previamente en la aplicación");
            return "error";
        }
    }

    @Operation(summary = "Get product type form", description = "Get the form to find all the products of a specific type", operationId = "getProductsByTypeForm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = { @Content(mediaType = "text/html")}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "text/html", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = { @Content(mediaType = "text/html", schema = @Schema(implementation = String.class))})
    })
    @GetMapping("/productTypeForm")
    public String getProductsByTypeForm(Authentication auth, Model model) throws Exception {
        if(auth != null) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String email = userDetails.getUsername();

            UserOutput loggedUser = getUserByEmailUseCase.execute(email);
            model.addAttribute("user", userMapper.fromUserOutputToUserDTO(loggedUser));

            return "type-product-form";
        }
        else{
            model.addAttribute("errorMessage", "Error: usuario sin autentificar.");
            model.addAttribute("errorDescription", "Para poder acceder a esta página es necesario que el " +
                    "usuario se haya identificado previamente en la aplicación");
            return "error";
        }
    }

    @Operation(summary = "Get products by type", description = "Get all the products of a specific type", operationId = "getProductsByType")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = { @Content(mediaType = "text/html")}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "text/html", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = { @Content(mediaType = "text/html", schema = @Schema(implementation = String.class))})
    })
    @PostMapping("/typeProducts")
    public String getProductsByType(Authentication auth, ModelMap model,
                @Parameter(description = "The name of the brand we wish to see the products of",
                schema = @Schema(type = "string"), example = "Lavadora") @Valid @RequestParam("productType")String productType) throws Exception {
        if(auth != null) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String email = userDetails.getUsername();

            UserOutput loggedUser = getUserByEmailUseCase.execute(email);
            model.addAttribute("user", userMapper.fromUserOutputToUserDTO(loggedUser));

            try{
                model.addAttribute("productsTypeList", getProductsByTypeUseCase.execute(productType)
                        .stream()
                        .map(productMapper::fromProductOutputToProductDTO)
                        .collect(Collectors.toList()));

                return getProductsByType(auth, model);

            } catch (Exception e) {
                model.addAttribute("formErrorMessage", e.getMessage());
            }
            return "type-product-form";
        }
        else{
            model.addAttribute("errorMessage", "Error: usuario sin autentificar.");
            model.addAttribute("errorDescription", "Para poder acceder a esta página es necesario que el " +
                    "usuario se haya identificado previamente en la aplicación");
            return "error";
        }
    }

    @Operation(summary = "Get products by type", description = "Show all the products of a specific type in a formatted table", operationId = "getProductsByType")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = { @Content(mediaType = "text/html")}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "text/html", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = { @Content(mediaType = "text/html", schema = @Schema(implementation = String.class))})
    })
    @GetMapping("/typeProducts")
    public String getProductsByType(Authentication auth, ModelMap model) throws Exception {
        if(auth != null) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String email = userDetails.getUsername();

            UserOutput loggedUser = getUserByEmailUseCase.execute(email);
            model.addAttribute("user", userMapper.fromUserOutputToUserDTO(loggedUser));

            return "products-by-type-list";
        }
        else{
            model.addAttribute("errorMessage", "Error: usuario sin autentificar.");
            model.addAttribute("errorDescription", "Para poder acceder a esta página es necesario que el " +
                    "usuario se haya identificado previamente en la aplicación");
            return "error";
        }
    }
}
