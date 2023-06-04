package com.civilo.roller.controllers;

import com.civilo.roller.Entities.IVAEntity;
import com.civilo.roller.services.IVAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
    @RequestMapping("/iva")
public class IVAController {
    @Autowired
    IVAService ivaService;

    // Permite obtener el IVA
    @GetMapping()
    public float getIVAPercentage(){
        return ivaService.getIVAPercentage();
    }

}
