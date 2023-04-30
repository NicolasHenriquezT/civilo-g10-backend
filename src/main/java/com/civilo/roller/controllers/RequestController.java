package com.civilo.roller.controllers;

import com.civilo.roller.Entities.RequestEntity;
import com.civilo.roller.Entities.UserEntity;
import com.civilo.roller.services.RequestService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/requests")
public class RequestController {
    @Autowired
    RequestService requestService;

    @GetMapping()
    public List<RequestEntity> getRequests(){
        return requestService.getRequests();
    }

    @PostMapping()
    public RequestEntity saveRequest(@RequestBody RequestEntity request){
        return this.requestService.saveRequest(request);
    }

    @PostMapping("/clientRequest")
    public ResponseEntity<?> createRequest(@RequestBody RequestEntity requestEntity, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            System.out.println("NO EXISTE SESIÓN ACTIVA -> NO SE ENVÍA LA SOLICITUD");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // Si hay una sesión activa, creamos la solicitud
        requestEntity.setUser((UserEntity) session.getAttribute("user"));
        // Validamos que el usuario que envía la solicitud es del tipo cliente
        if (((UserEntity) session.getAttribute("user")).getRole().getAccountType().equals("Cliente")){
            requestService.saveRequest(requestEntity);
            System.out.println("SOLICITUD ENVIADA CORRECTAMENTE");
            return ResponseEntity.ok().build();
        }
        System.out.println("USUARIO SIN PERMISOS PARA ESTA ACCIÓN -> NO SE ENVIA LA SOLICITUD");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
