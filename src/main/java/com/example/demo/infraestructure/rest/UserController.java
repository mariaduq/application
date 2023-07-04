package com.example.demo.infraestructure.rest;

import com.example.demo.application.SaveUserUseCase;
import com.example.demo.infraestructure.ddbb.PriceRepositoryJpa;
import com.example.demo.infraestructure.ddbb.UserRepositoryJpa;
import com.example.demo.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/signup")
public class UserController {

    @Autowired
    UserRepositoryJpa userRepositoryJpa;

    @Autowired
    private SaveUserUseCase saveUserUseCase;

    @ModelAttribute("user")
    public ResponseEntity<UserDTO> saveUser() {
        return ResponseEntity.ok().body(new UserDTO());
    }

    @GetMapping
    public String showRegistryForm() {
        return "user-form";
    }

    @PostMapping
    public String registerNewUserAccount(@ModelAttribute("user") UserDTO userDTO) {
        ModelMapper mapper = new ModelMapper();
        User user = mapper.map(userDTO, User.class);
        saveUserUseCase.execute(user);

        return "redirect:/user-form?exito";
    }
}
