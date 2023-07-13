package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public @Data class User {

    private Long id;

    private String nickname;

    private String name;

    private String surname;

    private String email;

    private String password;

    private String confirmPassword;
}
