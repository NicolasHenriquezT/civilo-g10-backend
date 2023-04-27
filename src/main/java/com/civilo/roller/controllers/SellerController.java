package com.civilo.roller.controllers;

import com.civilo.roller.Entities.SellerEntity;
import com.civilo.roller.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
