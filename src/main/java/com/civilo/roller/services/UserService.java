package com.civilo.roller.services;

import com.civilo.roller.Entities.UserEntity;
import com.civilo.roller.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    // El siguiente método retorna un listado el cual contiene TODA la información asociada a los usuarios
    public List<UserEntity> getUsers(){
        return (List<UserEntity>) userRepository.findAll();
    }

    // El siguiente método permite guardar un objeto del tipo "UserEntity" en la base de datos
    public UserEntity saveUser(UserEntity user){
        return userRepository.save(user);
    }

    public Optional<UserEntity> getUserById(Long id){
        return userRepository.findById(id);
    }

    public UserEntity validateUser(String email, String password){
        UserEntity user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)){
            return user;
        }
        return null;
    }

}
