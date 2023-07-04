package com.example.demo.application;

import com.example.demo.application.ports.UsersPort;
import com.example.demo.model.User;

public class SaveUserUseCase {

    private UsersPort usersPort;

    public SaveUserUseCase(UsersPort usersPort){
        this.usersPort=usersPort;
    }

    public User execute(User user) {
        return usersPort.save(user);
    }
}
