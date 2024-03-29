package com.example.demo.infrastructure.persistence.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USERS", uniqueConstraints = @UniqueConstraint(columnNames = "EMAIL"))
@AllArgsConstructor
@NoArgsConstructor
@Builder
public @Data class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "NAME")
    @NotBlank
    private String name;

    @Column(name = "SURNAME")
    @NotBlank
    private String surname;

    @Column(name = "NICKNAME")
    @NotBlank
    private String nickname;

    @Column(name = "EMAIL")
    @NotBlank
    private String email;

    @Column(name = "PASSWORD")
    @NotBlank
    private String password;

    @Column(name = "CONFIRM_PASSWORD")
    @NotBlank
    private String confirmPassword;

}
