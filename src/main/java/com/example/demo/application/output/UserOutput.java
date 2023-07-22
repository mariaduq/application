package com.example.demo.application.output;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserOutput {

    private Long id;

    private String nickname;

    private String name;

    private String surname;

    private String email;

    private String password;

    private String confirmPassword;
}
