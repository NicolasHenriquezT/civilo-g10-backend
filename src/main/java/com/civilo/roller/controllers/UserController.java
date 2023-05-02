package com.civilo.roller.controllers;

import com.civilo.roller.Entities.DataTransferObjectEntity;
import com.civilo.roller.Entities.UserEntity;
import com.civilo.roller.services.PermissionService;
import com.civilo.roller.services.RoleService;
import com.civilo.roller.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    PermissionService permissionService;

    @Autowired
    RoleService roleService;

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
        System.out.println(getSession(session));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null){
            System.out.println("SESIÓN CERRADA CORRECTAMENTE");
            session.invalidate();
            return ResponseEntity.ok().build();
        }
        System.out.println("NO HAY SESIÓN INICIADA");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/session")
    public String getSession(HttpSession session) {
        String sessionId = session.getId();
        UserEntity user = (UserEntity) session.getAttribute("user");
        return "Session ID: " + sessionId + "\n" +
                "User email      : " + user.getEmail() + "\n" +
                "User full name  : " + user.getName() + " " + user.getSurname() + "\n" +
                "User role       : " + user.getRole().getAccountType() + "\n" +
                "User role ID    : " + user.getRole().getRoleID() + "\n" +
                "User permissions: " + permissionService.rolePermissions(user.getRole().getRoleID()) + "\n";
    }

    //Registro de usuario
    @PostMapping("/register")
    public ResponseEntity<?> RegisterUser(@RequestBody UserEntity user) {

        //Se obtiene la id del rol dependiendo del tipo de cuenta
        String accountType = user.getRole().getAccountType();
        Long IdRol = roleService.getRoleIdByAccountType(accountType);

        //Se guarda la id del rol dentro del usuario
        user.getRole().setRoleID(IdRol);

        //Si el email ya esta registrado en la base de datos se manda un mensaje de respuesta al cliente
        //de que el usuario ya existe
        //y un codigo de respuesta 405
        if(userService.userAlreadyExists(user)){
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("El usuario ya existe");
        }
        
        //sino, se guarda el usuario en la base de datos y se manda el codigo de respuesta
        else{
            userService.saveUser(user);
            return ResponseEntity.ok().build();

        }       
        
    }
    






}
