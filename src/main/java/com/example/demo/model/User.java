package com.example.demo.model;

import jakarta.persistence.Column;
import lombok.Data;

public @Data class User {

    private Long id;

    private String nickname;

    private String name;

    private String surname;

    private String email;

    private String password;
}
