package com.civilo.roller.controllers;

import com.civilo.roller.Entities.RequestEntity;
import com.civilo.roller.Entities.StatusEntity;
import com.civilo.roller.Entities.UserEntity;
import com.civilo.roller.services.RequestService;
import com.civilo.roller.services.StatusService;
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
@RequestMapping("/requests")
public class RequestController {
    @Autowired
    RequestService requestService;

    @Autowired
    UserService userService;

    @Autowired
    StatusService statusService;

    @GetMapping()
    public List<RequestEntity> getRequests(){
        return requestService.getRequests();
    }

    @PostMapping()
    public RequestEntity saveRequest(@RequestBody RequestEntity request){
        return this.requestService.saveRequest(request);
    }

    @PostMapping("/clientRequest")
    public ResponseEntity<?> createRequest(@RequestBody RequestEntity requestEntity) {
        if (userService.validateUser(requestEntity.getUser().getEmail(), requestEntity.getUser().getPassword()) == null) {
            System.out.println("NO EXISTE SESIÓN ACTIVA -> NO SE ENVÍA LA SOLICITUD");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // Validamos que el usuario que envía la solicitud es del tipo cliente
        if (requestEntity.getUser().getRole().getAccountType().equals("Cliente")){
            requestEntity.setStatus(statusService.getStatus().get(0));
            requestService.saveRequest(requestEntity);
            System.out.println("SOLICITUD ENVIADA CORRECTAMENTE");
            return ResponseEntity.ok().build();
        }
        System.out.println("USUARIO SIN PERMISOS PARA ESTA ACCIÓN -> NO SE ENVIA LA SOLICITUD");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
