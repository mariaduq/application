package com.example.demo.infraestructure.rest;

import com.example.demo.application.GetUserByIdUseCase;
import com.example.demo.application.LoginUserUseCase;
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
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private SaveUserUseCase saveUserUseCase;

    @Autowired
    private LoginUserUseCase loginUserUseCase;

    @Autowired
    private GetUserByIdUseCase getUserByIdUseCase;

    private final UserMapper userMapper;

    @GetMapping({"/", "/homepage"})
    public String homepage() {
        return "index";
    }

    @GetMapping({"/loggedUser"})
    public String welcome(ModelMap model) {
        System.out.println(model.getAttribute("user"));
        return "loggedUser";
    }

    @GetMapping("/signup")
    public String getUserForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "user-form";
    }

    @PostMapping("/signup")
    public String createUser(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult result, ModelMap model) {
        if(result.hasErrors()) {
            model.addAttribute("user", userDTO);
        }
        else{
            try{
                saveUserUseCase.execute(userMapper.toUserInput(userDTO));
                model.addAttribute("user", new UserDTO());
                model.addAttribute("successMessage", "Successful registration. You can now access the app");
            } catch (Exception e) {
                model.addAttribute("formErrorMessage", e.getMessage());
                model.addAttribute("user", userDTO);
            }

        }
        return "user-form";
    }

    @GetMapping("/login")
    public String getLoginForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult result, ModelMap model) {
        if(result.hasErrors()) {
            model.addAttribute("user", userDTO);
            return "login";
        }
        else{
            try{
                User userFound = loginUserUseCase.execute(userMapper.toUserInput(userDTO));
                model.addAttribute("user", userFound);
                model.addAttribute("successMessage", "Successful login. You can now access the app");
                return welcome(model);
            } catch (Exception e) {
                model.addAttribute("formErrorMessage", e.getMessage());
                model.addAttribute("user", userDTO);
            }
        }
        return "login";
    }

    @GetMapping("/editUser/{id}")
    public String getEditUserForm(Model model, @PathVariable(name="id")Long id) {
        User userToEdit = getUserByIdUseCase.execute(id);
        model.addAttribute("user", userToEdit);
        model.addAttribute("editMode", "true");
        return "user-form";
    }

}
