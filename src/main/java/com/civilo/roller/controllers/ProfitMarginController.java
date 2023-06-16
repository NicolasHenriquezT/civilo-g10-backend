package com.civilo.roller.controllers;

import com.civilo.roller.Entities.ProfitMarginEntity;
import com.civilo.roller.services.ProfitMarginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/profitMargins")
public class ProfitMarginController {
    @Autowired
    ProfitMarginService profitMarginService;

    // Permite obtener todos los margenes de utilidad utilizados en el sistema.
    @GetMapping()
    public List<ProfitMarginEntity> getProfitMargins(){
        return profitMarginService.getProfitMargins();
    }
}
