package com.example.demo.application.ports;

import com.example.demo.domain.User;

public interface UsersPort {

    public User save(User newUser) throws Exception;

    public User login(User userLogin) throws Exception;

    public User getUserById(Long id);

    public User update(User userToUpdate) throws Exception;

    public User getUserByEmail(String email) throws Exception;

    public void deleteUser(Long id) throws Exception;

    public void updateForgotPassword(String newPassword, String email) throws Exception;

    public void changePassword(String email, String oldPassword, String newPassword, String confirmNewPassword) throws Exception;
}
