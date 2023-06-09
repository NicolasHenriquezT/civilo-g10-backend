package com.civilo.roller.controllers;

import com.civilo.roller.Entities.QuoteEntity;
import com.civilo.roller.services.IVAService;
import com.civilo.roller.services.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.Optional;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/quotes")
public class QuoteController {
    @Autowired
    QuoteService quoteService;

    @Autowired
    IVAService ivaService;

    // Permite obtener todas las cotizaciones del sistema.
    @GetMapping()
    public List<QuoteEntity> getQuotes(){
        return quoteService.getQuotes();
    }

    // Permite obtener los datos de una cotizacion en especifico.
    /*
    @GetMapping("/{id}")
    public ResponseEntity<QuoteEntity> getQuoteById(@PathVariable long id){
        Optional<QuoteEntity> quote = quoteService.getQuoteById(id);
        if(!quote.isPresent()){
            System.out.println("NO SE ENCONTRO LA COTIZACION \n");
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<QuoteEntity>(quote.get(), HttpStatus.OK);
    }

     */

    // Permite guardar entidad cotizacion.
    @PostMapping()
    public QuoteEntity saveQuote(@RequestBody QuoteEntity quote){
        Date currentDate = new Date();
        quote.setDate(currentDate);
        quote.setCurrentIVA(ivaService.getLastIVA());
        System.out.println(quote);
        return this.quoteService.createQuote(quote);
    }

    // Permite guardar una nueva cotizacion en el sistema.
    // FALTA: Solo los vendedores deberian poder hacerlo
    //@PostMapping("/register")
    //public ResponseEntity<?> createQuote(@RequestBody QuoteEntity quote){
    //    Optional<QuoteEntity> existingQuote = quoteService.
    //} 

    // Permite actualizar informacion de una cotizacion.
    // FALTA: Ver si solo administradores pueden hacer esto
    /*
    @PutMapping("/{id}")
    public ResponseEntity<?> updateQuote(@PathVariable long id, @RequestBody QuoteEntity quote){
        
        Optional<QuoteEntity> checkQuote = quoteService.getQuoteById(id);
        
        if(!checkQuote.isPresent()){
            System.out.println("NO SE ENCONTRO LA COTIZACION CON ID: " + id + " \n");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La cotizacion con el ID especificado no se encuentra registrada."); 
        }

        //Aca deberia ir chequeo si es administrador el tipo de cuenta

        quoteService.updateQuote(id,quote);
        System.out.println("ACTUALIZADO CON EXITO \n");
        return ResponseEntity.ok().build();
    }

     */

    // Permite eliminar todas las cotizaciones del sistema.
    /*
    @DeleteMapping()
    public ResponseEntity<String> deleteQuotes(){
        quoteService.deleteQuotes();
        return ResponseEntity.ok("SE ELIMINARON LAS COTIZACIONES CORRECTAMENTE");
    }

    // Permite elminar una cotizacion en especifico del sistema.
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuoteById(@PathVariable Long id){
        if(!quoteService.existsQuoteById(id)){
            System.out.println("NO SE ENCONTRO COTIZACION CON EL ID: " + id + "\n");
            return ResponseEntity.notFound().build();
        }
        quoteService.deleteQuoteById(id);
        return ResponseEntity.ok("COTIZACION CON ID " + id + " ELIMINADA CORRECTAMENTE \n");
    }

     */

    //------------------------------------------------------------------------------------------------------------------------------------------------//

}
