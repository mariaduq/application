package com.example.demo.infraestructure.ddbb;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.infraestructure.ddbb.model.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryJpa extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByNicknameOrEmail(String nickname, String email);
}
