package com.civilo.roller.controllers;

import com.civilo.roller.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sellers")
public class SellerController {
    @Autowired
    SellerService sellerService;
}
