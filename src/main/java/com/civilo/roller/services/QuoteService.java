package com.civilo.roller.services;

import com.civilo.roller.Entities.QuoteEntity;
import com.civilo.roller.repositories.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import com.civilo.roller.exceptions.EntityNotFoundException;
import java.util.Optional;
import java.util.List;

@Service
public class QuoteService {
    @Autowired
    QuoteRepository quoteRepository;

    // Get all
    // Permite obtener un listado con toda la informacion asociada a las solicitudes.
    public List<QuoteEntity> getQuotes(){
        return (List<QuoteEntity>) quoteRepository.findAll();
    }

    // Get by id
    // Permite obtener la informacion de una solicitud en especifico.
    public Optional<QuoteEntity> getQuoteById(Long id){ 
        return quoteRepository.findById(id);
    }

    // Permite guardar un objeto del tipo "QuoteEntity" en la base de datos.
    public QuoteEntity saveQuote(QuoteEntity quote){
        return quoteRepository.save(quote);
    }

    // Create
    // Permite guardar un objeto del tipo "QuoteEntity" en la base de datos.
    public QuoteEntity createQuote(QuoteEntity quote){
        return quoteRepository.save(quote);
    }

    // Update
    // Permite actualizar los datos de un objeto del tipo "QuoteEntity" en la base de datos. 
    public QuoteEntity updateQuote(Long quoteID, QuoteEntity quote){

        QuoteEntity existingQuote = quoteRepository.findById(quoteID)
            .orElseThrow(() -> new EntityNotFoundException("Cotizacion no encontrada con el ID: " + quoteID));

        existingQuote.setProductName(quote.getProductName());
        existingQuote.setAmount(quote.getAmount());
        existingQuote.setValue(quote.getValue());
        existingQuote.setDescription(quote.getDescription());
        existingQuote.setCommission(quote.getCommission());

        //Falta asignar id del vendedor

        QuoteEntity updatedQuote = quoteRepository.save(existingQuote);
        return updatedQuote;
    }

    // Delete all
    // Permite eliminar todas las cotizaciones de un sistema.
    public void deleteQuotes(){
        quoteRepository.deleteAll();
    }

    // Delete by id
    // Permite eliminar una cotizacion en especifico del sistema.
    public void deleteQuoteById(Long id){
        quoteRepository.deleteById(id);
    }

    // Permite verificar si existe una cotizacion en el sistema, segun el id ingresado.
    public boolean existsQuoteById(Long id){
        return quoteRepository.findById(id).isPresent();
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------//






}
