package com.example.demo.infraestructure.rest.controller;

import com.example.demo.application.usecases.user.*;
import com.example.demo.infraestructure.rest.UserDTO;
import com.example.demo.infraestructure.rest.mappers.UserMapper;
import com.example.demo.domain.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;

import java.security.SecureRandom;
import java.util.Random;

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

    @Autowired
    private final DeleteUserUseCase deleteUserUseCase;

    @Autowired
    private final SendEmailUseCase sendEmailUseCase;

    @Autowired
    private final UpdateForgotPasswordUseCase updateForgotPasswordUseCase;

    @Autowired
    private final ChangePasswordUseCase changePasswordUseCase;

    private final UserMapper userMapper;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private static final int LENGTH = 10;

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
        return "No authenticated user";
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

                Context context = new Context();
                context.setVariable("name", userDTO.getName());
                context.setVariable("surname", userDTO.getSurname());
                sendEmailUseCase.execute(userDTO.getEmail(), "Bienvenido a Electroshop!", "email-sign-up-template", context);

                model.addAttribute("successMessage", "Successful registration. You can now access the app");
            } catch (Exception e) {
                model.addAttribute("formErrorMessage", e.getMessage());
                model.addAttribute("user", userDTO);
                return "user-form";
            }

        }
        return "login";
    }

    @GetMapping("/login")
    public String getLoginForm() {
        return "login";
    }

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

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(ModelMap model, @PathVariable(name="id")Long id) {
        try{
            deleteUserUseCase.execute(id);
        } catch (Exception e) {
            model.addAttribute("formErrorMessage", e.getMessage());
        }
        return homepage();
    }

    @GetMapping("/forgotPassword")
    public String getForgotPasswordForm() {
        return "forgot_password";
    }

    @PostMapping("/forgotPassword")
    public String forgotPassword(ModelMap model, @Valid @ModelAttribute("email") String email) {
        try{
            model.addAttribute("email", email);

            UserDTO userFound = userMapper.fromUserToUserDTO(getUserByEmailUseCase.execute(email));
            String newGeneratedPassword = generateRandomPassword();

            updateForgotPasswordUseCase.execute(newGeneratedPassword, email);

            Context context = new Context();
            context.setVariable("newPassword", newGeneratedPassword);
            sendEmailUseCase.execute(email, "Contraseña olvidada", "email-template", context);

            model.addAttribute("successMessage", "An e-mail with a new password has been sent to you.");

        } catch (Exception e) {
            model.addAttribute("formErrorMessage", e.getMessage());
        }
        return getForgotPasswordForm();
    }

    public String generateRandomPassword() {
        Random random = new SecureRandom();
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i=0; i<LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }

    @GetMapping("/editUser/changePassword")
    public String getChangePasswordForm(Authentication auth, Model model) throws Exception {
        if(auth != null) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String email = userDetails.getUsername();

            User loggedUser = getUserByEmailUseCase.execute(email);
            model.addAttribute("user", loggedUser);

            return "change-password";
        }
        return "No authenticated user";
    }

    @PostMapping("/editUser/changePassword")
    public String changePassword(Authentication auth, ModelMap model, @Valid @ModelAttribute("oldPassword") String oldPassword,
                                 @Valid @ModelAttribute("newPassword") String newPassword,
                                 @Valid @ModelAttribute("confirmNewPassword") String confirmNewPassword) throws Exception {
        if(auth != null) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String email = userDetails.getUsername();

            User loggedUser = getUserByEmailUseCase.execute(email);
            model.addAttribute("user", loggedUser);

            try{
                model.addAttribute("oldPassword", oldPassword);
                model.addAttribute("newPassword", newPassword);
                model.addAttribute("confirmNewPassword", confirmNewPassword);

                changePasswordUseCase.execute(email, oldPassword, newPassword, confirmNewPassword);

                model.addAttribute("successMessage", "Password successfully updated");

            } catch(Exception e) {
                model.addAttribute("formErrorMessage", e.getMessage());
            }
            return "change-password";
        } else return "No authenticated user";
    }
}
