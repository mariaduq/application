package com.example.demo.application.input;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInput {

    private Long id;

    private String nickname;

    private String name;

    private String surname;

    private String email;

    private String password;

    private String confirmPassword;
}
