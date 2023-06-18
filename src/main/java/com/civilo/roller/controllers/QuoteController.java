package com.civilo.roller.controllers;

import com.civilo.roller.Entities.QuoteEntity;
import com.civilo.roller.Entities.QuoteSummaryEntity;
import com.civilo.roller.services.IVAService;
import com.civilo.roller.services.QuoteService;
import com.civilo.roller.services.QuoteSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.springframework.http.MediaType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
    QuoteSummaryService quoteSummaryService;

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
    public ResponseEntity<QuoteSummaryEntity> saveQuotes(@RequestBody List<QuoteEntity> quoteList){
        for (int i = 0; i < quoteList.size(); i ++) {
            if (quoteList.get(i).getAmount() == 0){
                quoteList.remove(quoteList.get(i));
                i--;
            }
        }
        for (int i = 0; i < quoteList.size(); i ++) {
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("Cotización del tipo de cortina: " + quoteList.get(i).getCurtain().getCurtainType());
            quoteService.calculation(quoteList.get(i));
        }
        QuoteSummaryEntity quoteSummary = quoteSummaryService.summaryCalculation(quoteList);
        for (int i = 0; i < quoteList.size(); i ++) {
            quoteList.get(i).setQuoteSummary(quoteSummary);
        }
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Resumen de cotización: ");
        System.out.println("Costo total de producción (CLP) = Sum(Costo de producción)                    = " + quoteSummary.getTotalCostOfProduction() +
                           "\nValor de venta total (CLP)      = Sum(Valor de venta)                         = " + quoteSummary.getTotalSaleValue() +
                           "\nValor tras descuento (CLP)      = Valor de venta total × Descuento / 100      = " + quoteSummary.getValueAfterDiscount() +
                           "\nTotal neto (CLP)                = Valor de venta total - Valor tras descuento = " + quoteSummary.getNetTotal() +
                           "\nTotal (CLP)                     = Total neto × (1 + IVA / 100)                = " + quoteSummary.getTotal());
        this.quoteService.createQuotes(quoteList);
        return ResponseEntity.ok(quoteSummary);
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


    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> generatePDFquote(@PathVariable("id") Long id) {
        // Obtener la cotización por su ID desde la base de datos (o servicio correspondiente)
        Optional<QuoteEntity> quote = quoteService.getQuoteById(id);

        try (PDDocument document = new PDDocument();
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            // Crear una nueva página
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            // Crear el contenido del PDF
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Escribir los datos de la cotización en el PDF
                contentStream.beginText();
                //contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(50, 700);
                contentStream.showText("ID de cotizacion: " + id);
                // Agregar más campos de la cotización según sea necesario
                contentStream.endText();
            }

            // Guardar el documento PDF en un flujo de bytes
            document.save(baos);
            document.close();

            // Obtener los bytes del PDF generado
            byte[] pdfBytes = baos.toByteArray();

            // Devolver una respuesta HTTP con los bytes del PDF y los encabezados adecuados
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header("Content-Disposition", "attachment; filename=cotizacion.pdf")
                    .body(pdfBytes);
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar cualquier error que pueda ocurrir al generar el PDF
            // Devolver una respuesta de error apropiada si es necesario
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



}
