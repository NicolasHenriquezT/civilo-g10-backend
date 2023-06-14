package com.civilo.roller.services;

import com.civilo.roller.Entities.QuoteEntity;
import com.civilo.roller.repositories.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import com.civilo.roller.exceptions.EntityNotFoundException;

import java.util.Date;
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
    /*
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

     */

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

    //Función que permite extraer/cálcular y settear las variables referentes a la fecha, total en metros
    //cuadrados (área), valor total de telas (fabrics), valor total de materiales, valor total de mano
    //de obra (labor), valor total de producción y valor total de venta
    public void calculation(QuoteEntity quote) {
        Date currentDate = new Date();
        quote.setDate(currentDate);
        quote.setTotalSquareMeters((int) Math.ceil(quote.getHeight() * quote.getWidth() * quote.getAmount()));
        System.out.println("Metros cuadrados: " + (int) Math.ceil(quote.getHeight() * quote.getWidth() * quote.getAmount()));
        quote.setTotalFabrics((int) Math.ceil(quote.getHeight() * quote.getValueSquareMeters() * quote.getAmount()));
        System.out.println("Total fabricación: " + (int) Math.ceil(quote.getHeight() * quote.getValueSquareMeters() * quote.getAmount()));
        quote.setTotalMaterials((int) Math.ceil(
                (calculateBracket(quote.getBracketValue(), quote.getAmount()) +
                        calculateCap(quote.getCapValue(), quote.getAmount()) +
                        calculatePipe(quote.getPipeValue(), quote.getAmount(), quote.getWidth()) +
                        calculateCounterweight(quote.getCounterweightValue(), quote.getAmount(), quote.getWidth()) +
                        calculateBand(quote.getBandValue(), quote.getAmount(), quote.getWidth()) +
                        calculateChain(quote.getChainValue(), quote.getAmount(), quote.getWidth()))
        ));
        System.out.println("Total materiales: " +
                (int) Math.ceil(
                        (calculateBracket(quote.getBracketValue(), quote.getAmount()) +
                            calculateCap(quote.getCapValue(), quote.getAmount()) +
                                calculatePipe(quote.getPipeValue(), quote.getAmount(), quote.getWidth()) +
                                calculateCounterweight(quote.getCounterweightValue(), quote.getAmount(), quote.getWidth()) +
                                calculateBand(quote.getBandValue(), quote.getAmount(), quote.getWidth()) +
                                calculateChain(quote.getChainValue(), quote.getAmount(), quote.getWidth()))
                        )
        );
        quote.setTotalLabor((int) Math.ceil((quote.getAssemblyValue() + quote.getInstallationValue()) * quote.getAmount()));
        System.out.println("Total MO: " + (int) Math.ceil((quote.getAssemblyValue() + quote.getInstallationValue()) * quote.getAmount()));
        quote.setProductionCost((int) Math.ceil(quote.getTotalLabor() + quote.getTotalMaterials() + quote.getTotalFabrics()));
        System.out.println("Total Costo producción: " + (int) Math.ceil((quote.getTotalLabor() + quote.getTotalMaterials() + quote.getTotalFabrics())));
        quote.setSaleValue((int) Math.ceil((quote.getProductionCost() / (1 - 0.4f))));
        System.out.println("Total Venta: " + (int) Math.ceil((quote.getProductionCost() / (1 - 0.4f))));
    }

    public float calculateBracket(float bracket, int amount){
        return bracket * amount;
    }

    public float calculateCap(float cap, int amount){
        return cap * amount;
    }

    public float calculatePipe(float pipe, int amount, float width){
        return pipe * amount * width;
    }

    public float calculateBand(float band, int amount, float width){
        return band * amount * width;
    }

    public float calculateChain(float chain, int amount, float width){
        return chain * amount * width;
    }

    public float calculateCounterweight(float counterWeight, int amount, float width){
        return counterWeight * amount * width;
    }



    // Función que recibe una lista de elementos QuoteEntity para agregarlos a la base de datos uno a uno
    public void createQuotes(List<QuoteEntity> quoteList){
        for (int i = 0; i < quoteList.size(); i ++){
            quoteRepository.save(quoteList.get(i));
        }
    }
}
