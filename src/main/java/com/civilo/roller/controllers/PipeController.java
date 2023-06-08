package com.civilo.roller.controllers;

import com.civilo.roller.Entities.PipeEntity;
import com.civilo.roller.services.PipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/pipes")
public class PipeController {
    @Autowired
    PipeService pipeService;

    // Permite obtener todos los permisos del sistema.
    @GetMapping()
    public List<PipeEntity> getPipes(){
        return pipeService.getPipes();
    }
}
