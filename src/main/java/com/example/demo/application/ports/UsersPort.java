package com.example.demo.application.ports;

import com.example.demo.application.UserInput;
import com.example.demo.model.User;
import jakarta.persistence.EntityNotFoundException;

public interface UsersPort {

    public User save(User newUser) throws Exception;

    public User login(User userLogin) throws Exception;

    public User getUserById(Long id);

    public User update(User userToUpdate) throws Exception;

    public User getUserByEmail(String email) throws Exception;

    public void deleteUser(Long id) throws Exception;

    public void updatePassword(String newPassword, String email) throws Exception;
}
