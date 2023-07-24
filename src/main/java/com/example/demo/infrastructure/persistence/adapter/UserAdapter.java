package com.example.demo.infrastructure.persistence.adapter;

import com.example.demo.domain.port.UsersPort;
import com.example.demo.infrastructure.persistence.jpa.UserRepositoryJpa;
import com.example.demo.infrastructure.persistence.mappers.UserMapper;
import com.example.demo.infrastructure.persistence.entities.UserEntity;
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
            } else throw new Exception("Contraseña incorrecta.");
        } else throw new EntityNotFoundException("No existe ningún usuario registrado con ese email.");
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.toDomain(userRepositoryJpa.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public User update(User updateUser) throws Exception {
        UserEntity userFound = userRepositoryJpa.findById(updateUser.getId())
                .orElseThrow(() -> new Exception("Este usuario no existe."));

        if(bCryptPasswordEncoder.matches(updateUser.getPassword(), userFound.getPassword())) {
            userFound.setName(updateUser.getName());
            userFound.setSurname(updateUser.getSurname());
            userFound.setNickname(updateUser.getNickname());
            userFound.setEmail(updateUser.getEmail());
        } else throw new Exception("Contraseña incorrecta.");

        return userMapper.toDomain(userRepositoryJpa.save(userFound));
    }

    @Override
    public User getUserByEmail(String email) throws Exception {
        return userMapper.toDomain(userRepositoryJpa.findByEmail(email).orElseThrow(() -> new Exception("No existe ningún usuario registrado con ese email.")));
    }

    @Override
    public void deleteUser(Long id) throws Exception {
        UserEntity userToDelete = userRepositoryJpa.findById(id).orElseThrow(() -> new Exception("No hay ningún usuario logueado."));
        userRepositoryJpa.delete(userToDelete);
    }

    @Override
    public void updateForgotPassword(String newPassword, String email) throws Exception {

        UserEntity userToUpdatePassword = userRepositoryJpa.findByEmail(email)
                .orElseThrow(() -> new Exception("Este usuario no existe."));

        String encodedPassword = bCryptPasswordEncoder.encode(newPassword);

        userToUpdatePassword.setPassword(encodedPassword);
        userToUpdatePassword.setConfirmPassword(encodedPassword);

        userMapper.toDomain(userRepositoryJpa.save(userToUpdatePassword));
    }

    @Override
    public void changePassword(String email, String oldPassword, String newPassword, String confirmNewPassword) throws Exception {
        UserEntity userToChangePassword = userRepositoryJpa.findByEmail(email)
                .orElseThrow(() -> new Exception("Este usuario no existe."));

        if(bCryptPasswordEncoder.matches(oldPassword, userToChangePassword.getPassword())) {
            if (newPassword.equals(confirmNewPassword)) {
                String encodedPassword = bCryptPasswordEncoder.encode(newPassword);
                userToChangePassword.setPassword(encodedPassword);
                userToChangePassword.setConfirmPassword(encodedPassword);
                userRepositoryJpa.save(userToChangePassword);
            } else throw new Exception("La nueva contraseña no coincide con su confirmación.");
        } else throw new Exception("Contraseña actual incorrecta.");
    }

    private boolean checkNicknameAndEmailAvailable(User user) throws Exception {
        Optional<UserEntity> userFound = userRepositoryJpa.findByNicknameOrEmail(user.getNickname(), user.getEmail());
        if(userFound.isPresent()) {
            if(userFound.get().getNickname().equals(user.getNickname()) && userFound.get().getEmail().equals(user.getEmail())) {
                throw new Exception("Nick y email no disponibles.");
            }
            else if(userFound.get().getNickname().equals(user.getNickname())) {
                throw new Exception("Nick no disponible.");
            }
            else if(userFound.get().getEmail().equals(user.getEmail())) {
                throw new Exception("Ya existe una cuenta asociada a este email.");
            }
        }
        return true;
    }

    private boolean checkPasswordMatch(User user) throws Exception {
        if(!user.getPassword().equals(user.getConfirmPassword())) {
            throw new Exception("Las contraseñas no coinciden.");
        }
        return true;
    }

    private boolean checkPassword(User userFound, String loginPassword) {
        if(userFound.getPassword().equals(loginPassword)) return true;
        else return false;
    }
}
