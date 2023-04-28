package com.civilo.roller.controllers;

import com.civilo.roller.Entities.QuoteEntity;
import com.civilo.roller.services.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quotes")
public class QuoteController {
    @Autowired
    QuoteService quoteService;

    @GetMapping()
    public List<QuoteEntity> getQuotes(){
        return quoteService.getQuotes();
    }

    @PostMapping()
    public QuoteEntity saveQuote(@RequestBody QuoteEntity quote){
        return this.quoteService.saveQuote(quote);
    }
}
