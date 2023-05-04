package com.civilo.roller.controllers;

import com.civilo.roller.Entities.SellerEntity;
import com.civilo.roller.services.SellerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/sellers")
public class SellerController {
    @Autowired
    SellerService sellerService;

    @GetMapping()
    public List<SellerEntity> getSellers(){
        return sellerService.getSellers();
    }

    @PostMapping()
    public SellerEntity saveSeller(@RequestBody SellerEntity seller){
        return this.sellerService.saveSeller(seller);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody SellerEntity userDTO, HttpServletRequest request){
        SellerEntity seller = sellerService.validateSeller(userDTO.getEmail(), userDTO.getPassword());
        if (seller == null){
            System.out.println("CORREO O CONTRASEÑA INCORRECTA\n");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        HttpSession session = request.getSession();
        session.setAttribute("seller", seller);
        System.out.println("SESIÓN INICIADA CORRECTAMENTE");
        return ResponseEntity.ok().build();
    }

}
