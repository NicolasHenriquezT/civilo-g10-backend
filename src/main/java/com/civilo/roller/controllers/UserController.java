package com.civilo.roller.controllers;

import com.civilo.roller.Entities.UserEntity;
import com.civilo.roller.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping()
    public List<UserEntity> getUsers(){
        return userService.getUsers();
    }

    @PostMapping()
    public UserEntity saveUser(@RequestBody UserEntity user){
        return this.userService.saveUser(user);
    }
}
