package com.example.demo.application.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInput {

    private Long id;

    private String nickname;

    private String name;

    private String surname;

    private String email;

    private String password;

    private String confirmPassword;
}
