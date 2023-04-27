package com.civilo.roller.controllers;

import com.civilo.roller.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/requests")
public class RequestController {
    @Autowired
    RequestService requestService;
}
