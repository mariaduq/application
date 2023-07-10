package com.example.demo.infraestructure.rest;

import com.example.demo.application.*;
import com.example.demo.infraestructure.ddbb.PriceRepositoryJpa;
import com.example.demo.infraestructure.ddbb.UserRepositoryJpa;
import com.example.demo.infraestructure.rest.mappers.UserMapper;
import com.example.demo.model.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Autowired
    private UpdateUserUseCase updateUserUseCase;

    @Autowired
    private GetUserByEmailUseCase getUserByEmailUseCase;

    private final UserMapper userMapper;

    @GetMapping({"/", "/homepage"})
    public String homepage() {
        return "index";
    }

    @GetMapping({"/loggedUser"})
    public String welcome(Authentication auth, Model model) throws Exception {
        if(auth != null) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String email = userDetails.getUsername();

            User loggedUser = getUserByEmailUseCase.execute(email);
            model.addAttribute("user", loggedUser);
            return "loggedUser";
        }
        return "No hay ningún usuario autenticado";
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
                return "user-form";
            }

        }
        return /*getLoginForm(model, userDTO)*/ "login";
    }

    @GetMapping("/login")
    public String getLoginForm(/*ModelMap model, UserDTO userDTO*/) {
        //model.addAttribute("user", userDTO);
        return "login";
    }

    /*@PostMapping("/login")
    public String login(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult result, ModelMap model) {
        if(result.hasErrors()) {
            model.addAttribute("user", userDTO);
            System.out.println("Hola1");
            System.out.println(userDTO);
            return "login";
        }
        else{
            try{
                User userFound = loginUserUseCase.execute(userMapper.toUserInput(userDTO));
                model.addAttribute("user", userFound);
                model.addAttribute("successMessage", "Successful login. You can now access the app");
                System.out.println(userFound);
                return welcome(userMapper.fromUserToUserDTO(userFound), result, model);
            } catch (Exception e) {
                model.addAttribute("formErrorMessage", e.getMessage());
                System.out.println("Hola 2");
                System.out.println(userDTO);
                model.addAttribute("user", userDTO);
            }
        }
        return "login";
    }*/

    @GetMapping("/editUser/{id}")
    public String getEditUserForm(ModelMap model, @PathVariable(name="id")Long id) {
        User userToEdit = getUserByIdUseCase.execute(id);
        model.addAttribute("user", userToEdit);
        model.addAttribute("editMode", "true");
        return "user-form";
    }

    @PostMapping("/editUser")
    public String editUser(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult result, ModelMap model) {

        System.out.println(userDTO);
        if(result.hasErrors()) {
            model.addAttribute("user", userDTO);
            model.addAttribute("editMode", "true");
        }
        else{
            try{
                updateUserUseCase.execute(userMapper.toUserInput(userDTO));
                model.addAttribute("user", userDTO);
                model.addAttribute("successMessage", "Profile successfully edited!");
                model.addAttribute("editMode", "true");
                //return welcome(model);
            } catch (Exception e) {
                model.addAttribute("formErrorMessage", e.getMessage());
                model.addAttribute("user", userDTO);
                model.addAttribute("editMode", "true");

            }

        }
        return "user-form";
    }

    @GetMapping("/editUser/cancel")
    public String cancelEditUser(ModelMap model) {
        return "redirect:/loggedUser";
    }

    @GetMapping("/signup/cancel")
    public String cancelCreateUser(ModelMap model) {
        return "redirect:/homepage";
    }
}
