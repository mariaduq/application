package com.example.demo.infraestructure.ddbb;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.infraestructure.ddbb.model.UserEntity;

public interface UserRepositoryJpa extends JpaRepository<UserEntity, Long> {
    
}
