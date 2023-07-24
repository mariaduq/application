package com.example.demo.infrastructure.rest.controller;

import com.example.demo.application.output.UserOutput;
import com.example.demo.application.usecases.user.*;
import com.example.demo.infrastructure.rest.dto.UserDTO;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;

import java.security.SecureRandom;
import java.util.Random;

@Controller
@RequiredArgsConstructor
@RequestMapping("/application/v1")
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

    @Operation(summary = "Homepage", description = "Get the homepage of the application", operationId = "homepage")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = { @Content(mediaType = "text/html") })
    })
    @GetMapping({"/", "/homepage"})
    public String homepage() {
        return "index";
    }

    @Operation(summary = "Get logged user page", description = "Get the page accessed by the user when logging into the application", operationId = "welcome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = { @Content(mediaType = "text/html")}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "text/html", schema = @Schema(implementation = String.class))}),
    })
    @GetMapping({"/loggedUser"})
    public String welcome(Authentication auth, Model model) throws Exception {
        if(auth != null) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String email = userDetails.getUsername();

            UserOutput loggedUser = getUserByEmailUseCase.execute(email);
            model.addAttribute("user", loggedUser);

            return "logged-user";
        }
        else{
            model.addAttribute("errorMessage", "Error: usuario sin autentificar.");
            model.addAttribute("errorDescription", "Para poder acceder a esta página es necesario que el " +
                    "usuario se haya identificado previamente en la aplicación");
            return "error";
        }
    }

    @Operation(summary = "Get signup form", description = "Get the application registration form", operationId = "getUserForm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = { @Content(mediaType = "text/html")})
    })
    @GetMapping("/signup")
    public String getUserForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "user-form";
    }

    @Operation(summary = "Create user", description = "Create a new user and stores the data", operationId = "createUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = { @Content(mediaType = "text/html")}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = { @Content(mediaType = "text/html", schema = @Schema(implementation = String.class))}),
    })
    @PostMapping("/signup")
    public String createUser(@Parameter(description = "The data of the new user who has registered",
            schema = @Schema(implementation = UserDTO.class)) @Valid @ModelAttribute("user") UserDTO userDTO, BindingResult result, ModelMap model) {
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
                sendEmailUseCase.execute(userDTO.getEmail(), "Bienvenid@ a ElectroShop", "email-sign-up-template", context);

                model.addAttribute("successMessage", "Registro exitoso. Ya puedes acceder a la aplicación.");
            } catch (Exception e) {
                model.addAttribute("formErrorMessage", e.getMessage());
                model.addAttribute("user", userDTO);
                return "user-form";
            }

        }
        return "login";
    }

    @Operation(summary = "Get login form", description = "Get the application login form", operationId = "getLoginForm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = { @Content(mediaType = "text/html")})
    })
    @GetMapping("/login")
    public String getLoginForm() {
        return "login";
    }

    @Operation(summary = "Get edit user form", description = "Get the form to edit the profile of the user who has logged in", operationId = "welcome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = { @Content(mediaType = "text/html")}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "text/html", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = { @Content(mediaType = "text/html", schema = @Schema(implementation = String.class))})
    })
    @GetMapping("/me/account/edit/{id}")
    public String getEditUserForm(Authentication auth, ModelMap model,
              @Parameter(description = "The ID of the user who has logged in", schema = @Schema(type = "integer", format = "int64"), example = "1")
              @PathVariable(name="id")Long id) {
        if(auth != null) {
            UserOutput userToEdit = getUserByIdUseCase.execute(id);
            model.addAttribute("user", userMapper.fromUserOutputToUserDTO(userToEdit));
            model.addAttribute("editMode", "true");
            return "user-form";
        }
        else{
            model.addAttribute("errorMessage", "Error: usuario sin autentificar.");
            model.addAttribute("errorDescription", "Para poder acceder a esta página es necesario que el " +
                    "usuario se haya identificado previamente en la aplicación");
            return "error";
        }
    }

    @Operation(summary = "Edit user", description = "Edit the user profile for the authenticated user", operationId = "editUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = { @Content(mediaType = "text/html")}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "text/html", schema = @Schema(implementation = String.class)) })
    })
    @PostMapping("/me/account/edit")
    public String editUser(Authentication auth,
               @Parameter(description = "The new data of the new user", schema = @Schema(implementation = UserDTO.class))
               @Valid @ModelAttribute("user") UserDTO userDTO, BindingResult result, ModelMap model) {
        if(auth != null) {
            if(result.hasErrors()) {
                model.addAttribute("user", userDTO);
                model.addAttribute("editMode", "true");
            }
            else{
                try{
                    updateUserUseCase.execute(userMapper.toUserInput(userDTO));
                    model.addAttribute("user", userDTO);
                    model.addAttribute("successMessage", "¡Perfil editado con éxito!");
                    model.addAttribute("editMode", "true");
                } catch (Exception e) {
                    model.addAttribute("formErrorMessage", e.getMessage());
                    model.addAttribute("user", userDTO);
                    model.addAttribute("editMode", "true");

                }

            }
            return "user-form";
        }
        else{
            model.addAttribute("errorMessage", "Error: usuario sin autentificar.");
            model.addAttribute("errorDescription", "Para poder acceder a esta página es necesario que el " +
                    "usuario se haya identificado previamente en la aplicación");
            return "error";
        }
    }

    @Operation(summary = "Cancel edit user", description = "Cancel the edit user operation and redirect to the logged user page", operationId = "cancelEditUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Found",
                    content = { @Content(mediaType = "text/html") })
    })
    @GetMapping("/me/account/edit/cancel")
    public String cancelEditUser(ModelMap model) {
        return "redirect:/application/v1/loggedUser";
    }

    @Operation(summary = "Cancel create user", description = "Cancel the user registration process and redirect to the homepage", operationId = "cancelCreateUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Found",
                    content = { @Content(mediaType = "text/html") })
    })
    @GetMapping("/signup/cancel")
    public String cancelCreateUser(ModelMap model) {
        return "redirect:/application/v1/homepage";
    }

    @Operation(summary = "Delete user", description = "Delete the user account", operationId = "deleteUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = { @Content(mediaType = "text/html") }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "text/html", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = { @Content(mediaType = "text/html", schema = @Schema(implementation = String.class))})
    })
    @GetMapping("/me/account/delete/{id}")
    public String deleteUser(Authentication auth, ModelMap model,
             @Parameter(description = "The ID of the user who wants to delete his account", schema = @Schema(type = "integer", format = "int64"), example = "1")
             @PathVariable(name="id")Long id) {
        if(auth != null) {
            try{
                UserDTO userDTO = userMapper.fromUserOutputToUserDTO(getUserByIdUseCase.execute(id));
                Context context = new Context();
                context.setVariable("name", userDTO.getName());
                sendEmailUseCase.execute(userDTO.getEmail(), "Cuenta eliminada", "email-delete-account-template", context);
                deleteUserUseCase.execute(id);
            } catch (Exception e) {
                model.addAttribute("formErrorMessage", e.getMessage());
            }
            return homepage();
        }
        else{
            model.addAttribute("errorMessage", "Error: usuario sin autentificar.");
            model.addAttribute("errorDescription", "Para poder acceder a esta página es necesario que el " +
                    "usuario se haya identificado previamente en la aplicación");
            return "error";
        }
    }

    @Operation(summary = "Get forgot password form", description = "Get the forgot password form to reset the user's password", operationId = "getForgotPasswordForm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = { @Content(mediaType = "text/html") })
    })
    @GetMapping("/login/forgotPassword")
    public String getForgotPasswordForm() {
        return "forgot_password";
    }

    @Operation(summary = "Forgot password", description = "Initiate the forgot password process to reset the user's password", operationId = "forgotPassword")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = { @Content(mediaType = "text/html") }),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = { @Content(mediaType = "text/html", schema = @Schema(implementation = String.class)) })
    })
    @PostMapping("/login/forgotPassword")
    public String forgotPassword(ModelMap model,
             @Parameter(description = "The email of the user who has forgotten his password",
                     schema = @Schema(type = "string", format = "email"), example = "i92durom@uco.es")
             @Valid @RequestParam("email") String email) {
        try{
            model.addAttribute("email", email);

            UserOutput userFound = getUserByEmailUseCase.execute(email);
            UserDTO userFoundDto = userMapper.fromUserOutputToUserDTO(userFound);

            String newGeneratedPassword = generateRandomPassword();

            updateForgotPasswordUseCase.execute(newGeneratedPassword, email);

            Context context = new Context();
            context.setVariable("name", userFoundDto.getName());
            context.setVariable("surname", userFoundDto.getSurname());
            context.setVariable("newPassword", newGeneratedPassword);
            sendEmailUseCase.execute(email, "Contraseña olvidada", "email-forgot-password-template", context);

            model.addAttribute("successMessage", "Te hemos enviado un mail para que puedas restablecer tu contraseña.");

        } catch (Exception e) {
            model.addAttribute("formErrorMessage", e.getMessage());
        }
        return getForgotPasswordForm();
    }

    private String generateRandomPassword() {
        Random random = new SecureRandom();
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i=0; i<LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }

    @Operation(summary = "Get change password form", description = "Get the change password form to modify the authenticated user's password", operationId = "getChangePasswordForm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = { @Content(mediaType = "text/html") }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "text/html", schema = @Schema(implementation = String.class))})
    })
    @GetMapping("/me/account/edit/changePassword")
    public String getChangePasswordForm(Authentication auth, Model model) throws Exception {
        if(auth != null) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String email = userDetails.getUsername();

            UserOutput loggedUser = getUserByEmailUseCase.execute(email);
            model.addAttribute("user", userMapper.fromUserOutputToUserDTO(loggedUser));

            return "change-password";
        }
        else{
            model.addAttribute("errorMessage", "Error: usuario sin autentificar.");
            model.addAttribute("errorDescription", "Para poder acceder a esta página es necesario que el " +
                    "usuario se haya identificado previamente en la aplicación");
            return "error";
        }
    }

    @Operation(summary = "Change password", description = "Change the authenticated user's password", operationId = "changePassword")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = { @Content(mediaType = "text/html") }),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = { @Content(mediaType = "text/html", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "text/html", schema = @Schema(implementation = String.class))})
    })
    @PostMapping("/me/account/edit/changePassword")
    public String changePassword(Authentication auth, ModelMap model,
             @Parameter(description = "The current password of the user",
                     schema = @Schema(type = "string", format = "password"))
             @Valid @RequestParam("oldPassword") String oldPassword,
             @Parameter(description = "The new password the user wants to set",
                     schema = @Schema(type = "string", format = "password"))
             @Valid @RequestParam("newPassword") String newPassword,
             @Parameter(description = "Confirm new password",
                     schema = @Schema(type = "string", format = "password"))
             @Valid @RequestParam("confirmNewPassword") String confirmNewPassword) throws Exception {
        if(auth != null) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String email = userDetails.getUsername();

            UserOutput loggedUser = getUserByEmailUseCase.execute(email);
            model.addAttribute("user", userMapper.fromUserOutputToUserDTO(loggedUser));

            try{
                model.addAttribute("oldPassword", oldPassword);
                model.addAttribute("newPassword", newPassword);
                model.addAttribute("confirmNewPassword", confirmNewPassword);

                changePasswordUseCase.execute(email, oldPassword, newPassword, confirmNewPassword);

                model.addAttribute("successMessage", "Contraseña editada con éxito.");

            } catch(Exception e) {
                model.addAttribute("formErrorMessage", e.getMessage());
            }
            return "change-password";
        }
        else{
            model.addAttribute("errorMessage", "Error: usuario sin autentificar.");
            model.addAttribute("errorDescription", "Para poder acceder a esta página es necesario que el " +
                    "usuario se haya identificado previamente en la aplicación");
            return "error";
        }
    }
}
