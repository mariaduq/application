package com.example.demo.infraestructure.rest;

import lombok.Data;

@Data
public class UserDTO {

    private Long id;

    private String nickname;

    private String name;

    private String surname;

    private String email;

    private String password;
}
