package com.civilo.roller.controllers;

import com.civilo.roller.Entities.StatusEntity;
import com.civilo.roller.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/status")
public class StatusController {
    @Autowired
    StatusService statusService;

    @GetMapping()
    public List<StatusEntity> getStatus(){
        return statusService.getStatus();
    }

    @PostMapping()
    public StatusEntity saveStatus(@RequestBody StatusEntity status){
        return this.statusService.saveStatus(status);
    }
}
