package com.example.demo.infraestructure.rest.controller;

import com.example.demo.application.usecases.product.GetAllProductsUseCase;
import com.example.demo.application.usecases.user.GetUserByEmailUseCase;
import com.example.demo.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private final GetAllProductsUseCase getAllProductsUseCase;

    @Autowired
    private GetUserByEmailUseCase getUserByEmailUseCase;

    @GetMapping("/productsList")
    public String getProductsList(Authentication auth, Model model) throws Exception {
        if(auth != null) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String email = userDetails.getUsername();

            User loggedUser = getUserByEmailUseCase.execute(email);
            model.addAttribute("user", loggedUser);

            try{
                model.addAttribute("productsList", getAllProductsUseCase.execute());
            } catch (Exception e) {
                model.addAttribute("formErrorMessage", e.getMessage());
            }
            return "products-list";
        }
        return "No authenticated user";
    }
}
