package com.civilo.roller.controllers;

import com.civilo.roller.Entities.UserEntity;
import com.civilo.roller.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    //Obtener lista de tareas (Metodo GET)
    //@RequestMapping(value = "/usuarios", method = RequestMethod.GET)
    //public List<UserEntity> getAllUsers(){
    //    return userService.getUsers();      
    //}

    @GetMapping()
    public List<UserEntity> getUsers(){
        return userService.getUsers();
    }

    @PostMapping()
    public UserEntity saveUser(@RequestBody UserEntity user){
        return this.userService.saveUser(user);
    }
}
