package com.civilo.roller.controllers;

import com.civilo.roller.Entities.DataTransferObjectEntity;
import com.civilo.roller.Entities.UserEntity;
import com.civilo.roller.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody DataTransferObjectEntity userDTO, HttpServletRequest request){
        UserEntity user = userService.validateUser(userDTO.getEmail(), userDTO.getPassword());
        if (user == null){
            System.out.println("CORREO O CONTRASEÑA INCORRECTA\n");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        System.out.println("SESIÓN INICIADA CORRECTAMENTE");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null){
            System.out.println("NO HAY SESIÓN INICIADA");
            session.invalidate();
        }
        System.out.println("SESIÓN CERRADA CORRECTAMENTE");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/session")
    public String getSession(HttpSession session) {
        String sessionId = session.getId();
        UserEntity user = (UserEntity) session.getAttribute("user");
        return "Session ID: " + sessionId + "\n" +
                "User email     : " + user.getEmail() + "\n" +
                "User full name : " + user.getName() + " " + user.getSurname() + "\n" +
                "User role      : " + user.getRole().getAccountType();
    }






}
