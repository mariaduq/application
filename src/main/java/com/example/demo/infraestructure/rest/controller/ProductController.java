package com.example.demo.infraestructure.rest.controller;

import com.example.demo.application.output.UserOutput;
import com.example.demo.application.usecases.product.GetAllProductsUseCase;
import com.example.demo.application.usecases.user.GetUserByEmailUseCase;
import com.example.demo.domain.model.User;
import com.example.demo.infraestructure.rest.mappers.ProductMapper;
import com.example.demo.infraestructure.rest.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/application/v1/products")
public class ProductController {

    @Autowired
    private final GetAllProductsUseCase getAllProductsUseCase;

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
}
