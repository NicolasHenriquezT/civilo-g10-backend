package com.civilo.roller.controllers;

import com.civilo.roller.services.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quotes")
public class QuoteController {
    @Autowired
    QuoteService quoteService;
}
