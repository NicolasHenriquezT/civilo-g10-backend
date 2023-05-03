package com.civilo.roller.services;

import com.civilo.roller.Entities.PermissionEntity;
import com.civilo.roller.Entities.UserEntity;
import com.civilo.roller.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;
import com.civilo.roller.exceptions.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    // Get all
    // Permite obtener un listado con toda la informacion asociada a los usuarios.
    public List<UserEntity> getUsers(){
        return (List<UserEntity>) userRepository.findAll();
    }
    
    // Get by id
    // Permite obtener la informacion de un usuario en especifico.
    public Optional<UserEntity> getUserById(Long id){
        return userRepository.findById(id);
    }

    // Create
    // Permite guardar un objeto del tipo "UserEntity" en la base de datos.
    public UserEntity createUser(UserEntity user){   
        return userRepository.save(user);  
    }
    
    // Permite verificar si existe un usuario registrado con el mismo email.
    public Optional<UserEntity> validateEmail(String email){
        return Optional.ofNullable(userRepository.findByEmail(email));  
    }
    
    // Update
    // Permite actualizar los datos de un objeto del tipo "UserEntity" en la base de datos.
    public UserEntity updateUser(Long userID, UserEntity user){
        System.out.println("ENTRO");
        
        //UserEntity existingUser = userRepository.findById(userID);

        UserEntity existingUser = userRepository.findById(userID)
            .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con el ID: " + userID));


        existingUser.setName(user.getName());
        existingUser.setSurname(user.getSurname());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setCommune(user.getCommune());
        existingUser.setBirthDate(user.getBirthDate()); //ESTO NO LO PUEDE EDITAR
        existingUser.setAge(user.getAge());  //ESTO NO LO PUEDE EDITAR 
        
        UserEntity updatedUser = userRepository.save(existingUser);
        return updatedUser;
    }

    // Delete all
    // Permite eliminar todos los usuarios de un sistema.
    public void deleteUsers() {
        userRepository.deleteAll();
    }

    // Delete by id
    // Permite eliminar un usuario en especifico del sistema.
    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }
    
    // Permite verificar si existe un usario en el sistema, segun el id ingresado.
    public boolean existsUserById(Long id){
        return userRepository.findById(id).isPresent();
    }
    
    //-----------------------------------------------------------------------------------------------------------------------------------------------//
    
    // Login
    // Permite verificar si el email y password de un usuario son correctos.
    public UserEntity validateUser(String email, String password){
        UserEntity user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)){
            return user;
        }
        return null;
    }
     
        

}
