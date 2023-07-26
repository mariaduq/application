package com.example.demo.domain.port;

import com.example.demo.domain.model.User;

public interface UsersPort {

    User save(User newUser) throws Exception;

    User login(User userLogin) throws Exception;

    User getUserById(Long id);

    User update(User userToUpdate) throws Exception;

    User getUserByEmail(String email) throws Exception;

    void deleteUser(Long id) throws Exception;

    void updateForgotPassword(String newPassword, String email) throws Exception;

    void changePassword(String email, String oldPassword, String newPassword, String confirmNewPassword) throws Exception;
}
