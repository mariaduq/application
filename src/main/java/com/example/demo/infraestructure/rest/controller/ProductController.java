package com.example.demo.infraestructure.rest.controller;

import com.example.demo.application.usecases.product.GetAllProductsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private final GetAllProductsUseCase getAllProductsUseCase;

    @GetMapping("/productsList")
    public String getProductsList(Model model) {
        model.addAttribute("productsList", getAllProductsUseCase.execute());
        return "products-list";
    }
}
