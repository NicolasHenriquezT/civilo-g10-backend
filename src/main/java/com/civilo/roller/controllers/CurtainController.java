package com.civilo.roller.controllers;

import com.civilo.roller.Entities.CurtainEntity;
import com.civilo.roller.services.CurtainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/curtains")
public class CurtainController {
    @Autowired
    CurtainService curtainService;

    @GetMapping()
    public List<CurtainEntity> getCurtains(){
        return curtainService.getCurtains();
    }

    @PostMapping()
    public CurtainEntity saveCurtain(@RequestBody CurtainEntity curtain){
        return this.curtainService.saveCurtain(curtain);
    }
}
