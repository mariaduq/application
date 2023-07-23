package com.example.demo.infrastructure.rest.controller;

import com.example.demo.application.output.UserOutput;
import com.example.demo.application.usecases.product.GetAllProductsUseCase;
import com.example.demo.application.usecases.product.GetProductsByBrandUseCase;
import com.example.demo.application.usecases.product.GetProductsByTypeUseCase;
import com.example.demo.application.usecases.user.GetUserByEmailUseCase;
import com.example.demo.infrastructure.rest.mappers.ProductMapper;
import com.example.demo.infrastructure.rest.mappers.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return "No authenticated user";
    }

    @GetMapping("/brandForm")
    public String getProductsByBrandForm(Authentication auth, Model model) throws Exception {
        if(auth != null) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String email = userDetails.getUsername();

            UserOutput loggedUser = getUserByEmailUseCase.execute(email);
            model.addAttribute("user", userMapper.fromUserOutputToUserDTO(loggedUser));

            return "brand-product-form";
        }
        return "No authenticated user";
    }

    @PostMapping("/brandProducts")
    public String getProductsByBrand(Authentication auth, ModelMap model, @Valid @ModelAttribute("brandName")String brandName) throws Exception {
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
        return "No authenticated user";
    }

    @GetMapping("/brandProducts")
    public String getProductsByBrand(Authentication auth, ModelMap model) throws Exception {
        if(auth != null) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String email = userDetails.getUsername();

            UserOutput loggedUser = getUserByEmailUseCase.execute(email);
            model.addAttribute("user", userMapper.fromUserOutputToUserDTO(loggedUser));

            return "products-by-brand-list";
        }
        return "No authenticated user";
    }

    @GetMapping("/productTypeForm")
    public String getProductsByTypeForm(Authentication auth, Model model) throws Exception {
        if(auth != null) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String email = userDetails.getUsername();

            UserOutput loggedUser = getUserByEmailUseCase.execute(email);
            model.addAttribute("user", userMapper.fromUserOutputToUserDTO(loggedUser));

            return "type-product-form";
        }
        return "No authenticated user";
    }

    @PostMapping("/typeProducts")
    public String getProductsByType(Authentication auth, ModelMap model, @Valid @ModelAttribute("productType")String productType) throws Exception {
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
        return "No authenticated user";
    }

    @GetMapping("/typeProducts")
    public String getProductsByType(Authentication auth, ModelMap model) throws Exception {
        if(auth != null) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String email = userDetails.getUsername();

            UserOutput loggedUser = getUserByEmailUseCase.execute(email);
            model.addAttribute("user", userMapper.fromUserOutputToUserDTO(loggedUser));

            return "products-by-type-list";
        }
        return "No authenticated user";
    }
}
