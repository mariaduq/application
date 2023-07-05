package com.example.demo.application.ports;

import com.example.demo.application.UserInput;
import com.example.demo.model.User;

public interface UsersPort {

    public User save(User newUser) throws Exception;
}
