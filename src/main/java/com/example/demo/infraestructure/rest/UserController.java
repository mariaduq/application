package com.example.demo.infraestructure.rest;

import com.example.demo.application.SaveUserUseCase;
import com.example.demo.infraestructure.ddbb.PriceRepositoryJpa;
import com.example.demo.infraestructure.ddbb.UserRepositoryJpa;
import com.example.demo.infraestructure.rest.mappers.UserMapper;
import com.example.demo.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/signup")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private SaveUserUseCase saveUserUseCase;

    private final UserMapper userMapper;

    @GetMapping
    public String getUserForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "user-form";
    }

    @PostMapping
    public String createUser(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult result, ModelMap model) {
        if(result.hasErrors()) {
            model.addAttribute("user", userDTO);
        }
        else{
            try{
                saveUserUseCase.execute(userMapper.toUserInput(userDTO));
            } catch (Exception e) {
                model.addAttribute("formErrorMessage", e.getMessage());
                model.addAttribute("user", userDTO);
            }

        }
        return "user-form";
    }
}
