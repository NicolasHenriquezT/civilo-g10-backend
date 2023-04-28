package com.civilo.roller.services;

import com.civilo.roller.Entities.QuoteEntity;
import com.civilo.roller.repositories.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuoteService {
    @Autowired
    QuoteRepository quoteRepository;

    // El siguiente método retorna un listado el cual contiene TODA la información asociada a las solicitudes
    public List<QuoteEntity> getQuotes(){
        return (List<QuoteEntity>) quoteRepository.findAll();
    }

    // El siguiente método permite guardar un objeto del tipo "QuoteEntity" en la base de datos
    public QuoteEntity saveQuote(QuoteEntity quote){
        return quoteRepository.save(quote);
    }


}
