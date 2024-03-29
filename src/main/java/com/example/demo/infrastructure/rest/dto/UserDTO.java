package com.example.demo.infrastructure.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    private String nickname;

    private String name;

    private String surname;

    private String email;

    private String password;

    private String confirmPassword;
}
