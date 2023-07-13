package com.example.demo.infraestructure.persistence.adapter;

import com.example.demo.domain.port.UsersPort;
import com.example.demo.infraestructure.persistence.jpa.UserRepositoryJpa;
import com.example.demo.infraestructure.persistence.mappers.UserMapper;
import com.example.demo.infraestructure.persistence.entities.UserEntity;
import com.example.demo.domain.model.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserAdapter implements UsersPort {

    private final UserRepositoryJpa userRepositoryJpa;

    private final UserMapper userMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User save(User newUser) throws Exception {
        if(checkNicknameAndEmailAvailable(newUser) && checkPasswordMatch(newUser)) {

            String encodedPassword = bCryptPasswordEncoder.encode(newUser.getPassword());
            String encodedConfirmPassword = bCryptPasswordEncoder.encode(newUser.getConfirmPassword());

            newUser.setPassword(encodedPassword);
            newUser.setConfirmPassword(encodedConfirmPassword);

            return userMapper.toDomain(userRepositoryJpa.save(userMapper.toEntity(newUser)));
        }
        return newUser;
    }

    @Override
    public User login(User userLogin) throws Exception {
        Optional<UserEntity> userFound = userRepositoryJpa.findByEmail(userLogin.getEmail());
        if(userFound.isPresent()){
            if(checkPassword(userMapper.toDomain(userFound.get()), userLogin.getPassword())) {
                return userMapper.toDomain(userFound.get());
            } else throw new Exception("Incorrect password. Try again.");
        } else throw new EntityNotFoundException("There is no user registered with this email. Try again.");
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.toDomain(userRepositoryJpa.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public User update(User updateUser) throws Exception {
        System.out.println(updateUser);
        UserEntity userFound = userRepositoryJpa.findById(updateUser.getId())
                .orElseThrow(() -> new Exception("This user doesn't exists"));

        if(bCryptPasswordEncoder.matches(updateUser.getPassword(), userFound.getPassword())) {
            userFound.setName(updateUser.getName());
            userFound.setSurname(updateUser.getSurname());
            userFound.setNickname(updateUser.getNickname());
            userFound.setEmail(updateUser.getEmail());
        } else throw new Exception("Incorrect password");

        return userMapper.toDomain(userRepositoryJpa.save(userFound));
    }

    @Override
    public User getUserByEmail(String email) throws Exception {
        return userMapper.toDomain(userRepositoryJpa.findByEmail(email).orElseThrow(() -> new Exception("There is no user registered with this email.")));
    }

    @Override
    public void deleteUser(Long id) throws Exception {
        UserEntity userToDelete = userRepositoryJpa.findById(id).orElseThrow(() -> new Exception("No user logged"));
        userRepositoryJpa.delete(userToDelete);
    }

    @Override
    public void updateForgotPassword(String newPassword, String email) throws Exception {

        UserEntity userToUpdatePassword = userRepositoryJpa.findByEmail(email)
                .orElseThrow(() -> new Exception("This user doesn't exists"));

        String encodedPassword = bCryptPasswordEncoder.encode(newPassword);

        userToUpdatePassword.setPassword(encodedPassword);
        userToUpdatePassword.setConfirmPassword(encodedPassword);

        userMapper.toDomain(userRepositoryJpa.save(userToUpdatePassword));
    }

    @Override
    public void changePassword(String email, String oldPassword, String newPassword, String confirmNewPassword) throws Exception {
        UserEntity userToChangePassword = userRepositoryJpa.findByEmail(email)
                .orElseThrow(() -> new Exception("This user doesn't exists"));

        if(bCryptPasswordEncoder.matches(oldPassword, userToChangePassword.getPassword())) {
            if (newPassword.equals(confirmNewPassword)) {
                String encodedPassword = bCryptPasswordEncoder.encode(newPassword);
                userToChangePassword.setPassword(encodedPassword);
                userToChangePassword.setConfirmPassword(encodedPassword);
                userRepositoryJpa.save(userToChangePassword);
            } else throw new Exception("New passwords do not match");
        } else throw new Exception("Incorrect old password");
    }

    private boolean checkNicknameAndEmailAvailable(User user) throws Exception {
        Optional<UserEntity> userFound = userRepositoryJpa.findByNicknameOrEmail(user.getNickname(), user.getEmail());
        if(userFound.isPresent()) {
            throw new Exception("Username or email already exists");
        }
        return true;
    }

    private boolean checkPasswordMatch(User user) throws Exception {
        if(!user.getPassword().equals(user.getConfirmPassword())) {
            throw new Exception("Passwords don't match");
        }
        return true;
    }

    private boolean checkPassword(User userFound, String loginPassword) {
        if(userFound.getPassword().equals(loginPassword)) return true;
        else return false;
    }
}
