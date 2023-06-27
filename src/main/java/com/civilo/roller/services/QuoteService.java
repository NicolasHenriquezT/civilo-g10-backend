package com.civilo.roller.services;

import com.civilo.roller.Entities.QuoteEntity;
import com.civilo.roller.Entities.QuoteSummaryEntity;
import com.civilo.roller.repositories.QuoteRepository;
import com.civilo.roller.repositories.QuoteSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import com.civilo.roller.exceptions.EntityNotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.List;

@Service
public class QuoteService {
    @Autowired
    QuoteRepository quoteRepository;

    @Autowired
    QuoteSummaryRepository quoteSummaryRepository;

    @Autowired
    ProfitMarginService profitMarginService;

    // Get all
    // Permite obtener un listado con toda la informacion asociada a las solicitudes.
    public List<QuoteEntity> getQuotes() {
        return (List<QuoteEntity>) quoteRepository.findAll();
    }

    // Get by id
    // Permite obtener la informacion de una solicitud en especifico.
    public Optional<QuoteEntity> getQuoteById(Long id) {
        return quoteRepository.findById(id);
    }

    // Permite guardar un objeto del tipo "QuoteEntity" en la base de datos.
    public QuoteEntity saveQuote(QuoteEntity quote) {
        return quoteRepository.save(quote);
    }

    // Create
    // Permite guardar un objeto del tipo "QuoteEntity" en la base de datos.
    public QuoteEntity createQuote(QuoteEntity quote) {
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
    public void deleteQuotes() {
        quoteRepository.deleteAll();
    }

    // Delete by id
    // Permite eliminar una cotizacion en especifico del sistema.
    public void deleteQuoteById(Long id) {
        quoteRepository.deleteById(id);
    }

    // Permite verificar si existe una cotizacion en el sistema, segun el id ingresado.
    public boolean existsQuoteById(Long id) {
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
        quote.setProfitMarginEntity(profitMarginService.getLastProfitMargin());
        System.out.println("Área (m2)                   = Ancho (m) × Alto (m) × Cantidad de cortinas                                                    = " + (int) Math.ceil(quote.getHeight() * quote.getWidth() * quote.getAmount()));
        quote.setTotalFabrics((int) Math.ceil(quote.getHeight() * quote.getValueSquareMeters() * quote.getAmount()));
        System.out.println("Total en telas (CLP)        = Alto (m) × Valor tela (m2) × Cantidad de cortinas                                              = " + (int) Math.ceil(quote.getHeight() * quote.getValueSquareMeters() * quote.getAmount()));
        quote.setTotalMaterials((int) Math.ceil(
                (calculateBracket(quote.getBracketValue(), quote.getAmount()) +
                        calculateCap(quote.getCapValue(), quote.getAmount()) +
                        calculatePipe(quote.getPipeValue(), quote.getAmount(), quote.getWidth()) +
                        calculateCounterweight(quote.getCounterweightValue(), quote.getAmount(), quote.getWidth()) +
                        calculateBand(quote.getBandValue(), quote.getAmount(), quote.getWidth()) +
                        calculateChain(quote.getChainValue(), quote.getAmount(), quote.getWidth()))
        ));
        System.out.println("Total en materiales (CLP)   = Valor brackets + Valor tapas + Valor tubos + Valor contrapesos + Valor zunchos + Valor cadenas = " +
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
        System.out.println("Total en mano de obra (CLP) = (Valor armado + Valor instalación) × Cantidad                                                  = " + (int) Math.ceil((quote.getAssemblyValue() + quote.getInstallationValue()) * quote.getAmount()));
        quote.setProductionCost((int) Math.ceil(quote.getTotalLabor() + quote.getTotalMaterials() + quote.getTotalFabrics()));
        System.out.println("Costo de producción (CLP)   = Total en mano de obra + Total en materiales + Total en telas                                   = " + (int) Math.ceil((quote.getTotalLabor() + quote.getTotalMaterials() + quote.getTotalFabrics())));
        quote.setSaleValue((int) Math.ceil((quote.getProductionCost() / (1 - profitMarginService.getLastProfitMargin().getDecimalProfitMargin()))));
        System.out.println("Valor de venta (CLP)        = Costo de producción ( 1 - Margen de utilidad)                                                  = " + (int) Math.ceil((quote.getProductionCost() / (1 - profitMarginService.getLastProfitMargin().getDecimalProfitMargin()))));
    }

    public float calculateBracket(float bracket, int amount) {
        return bracket * amount;
    }

    public float calculateCap(float cap, int amount) {
        return cap * amount;
    }

    public float calculatePipe(float pipe, int amount, float width) {
        return pipe * amount * width;
    }

    public float calculateBand(float band, int amount, float width) {
        return band * amount * width;
    }

    public float calculateChain(float chain, int amount, float width) {
        return chain * amount * width;
    }

    public float calculateCounterweight(float counterWeight, int amount, float width) {
        return counterWeight * amount * width;
    }


    // Función que recibe una lista de elementos QuoteEntity para agregarlos a la base de datos uno a uno
    public void createQuotes(List<QuoteEntity> quoteList) {
        for (int i = 0; i < quoteList.size(); i++) {
            quoteRepository.save(quoteList.get(i));
        }
    }

    public QuoteSummaryEntity lastQuoteSummary(Long idseller) {
        List<QuoteSummaryEntity> quoteSummaryEntities = (List<QuoteSummaryEntity>) quoteSummaryRepository.findAll();
        QuoteSummaryEntity quoteSummary = new QuoteSummaryEntity();
        for (int i = quoteSummaryEntities.size() - 1; i != 0; i--) {
            if (quoteSummaryEntities.get(i).getSeller().getUserID() == idseller) {
                quoteSummary = quoteSummaryEntities.get(i);
                return quoteSummary;
            }
        }
        return quoteSummary;
    }

    /*  
    // NUEVAA
    public QuoteSummaryEntity listQuoteSummary(Long idSeller) {
        List<QuoteSummaryEntity> quoteSummaryEntities = (List<QuoteSummaryEntity>) quoteSummaryRepository.findAll();
        List<QuoteEntity> quoteSummarySelected = new ArrayList<>();
        for (int i = 0; i < quoteSummaryEntities.size(); i++) {
            if (quoteSummaryEntities.get(i).getSeller().getUserID() == idSeller) {
                quoteSummarySelected.add(quoteSummaryEntities.get(i));
            }
        }
        return quoteEntitiesSelected;
    }

    // NUEVAA
    public List<QuoteEntity> listQuotes(List<QuoteSummaryEntity> listSummary, Long idQuoteSelected, Long idSeller) {
        List<QuoteEntity> quoteEntities = (List<QuoteEntity>) quoteRepository.findAll();
        List<QuoteEntity> quoteEntitiesSelected = new ArrayList<>();
        
        [ [3,34] , [3,33]  , [3, 32]       ]
        
        
        for (int i = 0; i < listSummary.size(); i++) {
            [3,34]
            for(int j = 0; j < quoteEntities.size(); j++) {
                if(listS)
                
            }
        }
    }
    */






    public List<QuoteEntity> lastQuotes(Long idQuoteSummary) {
        List<QuoteEntity> quoteEntities = (List<QuoteEntity>) quoteRepository.findAll();
        List<QuoteEntity> quoteEntitiesSelected = new ArrayList<>();
        for (int i = quoteEntities.size() - 1; i >= 0; i--) {

            if (quoteEntities.get(i).getQuoteSummary().getQuoteSummaryID() == idQuoteSummary) {
                quoteEntitiesSelected.add(quoteEntities.get(i));
            }
            
        }
        return quoteEntitiesSelected;
    }

    public String instalation(List<QuoteEntity> quotes){
        for (int i = 0; i < quotes.size(); i++){
            if (quotes.get(i).getInstallationValue() != 0){
                return "Si";
            }
        }
        return "No";
    }

}
