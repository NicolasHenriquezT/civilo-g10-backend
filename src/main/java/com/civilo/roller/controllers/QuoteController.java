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
import java.util.ArrayList;

//nuevos
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.http.HttpHeaders;
import java.io.FileOutputStream;
import java.net.MalformedURLException;

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
    
    @GetMapping("/{id}")
    public ResponseEntity<QuoteEntity> getQuoteById(@PathVariable long id){
        Optional<QuoteEntity> quote = quoteService.getQuoteById(id);
        if(!quote.isPresent()){
            System.out.println("NO SE ENCONTRO LA COTIZACION \n");
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<QuoteEntity>(quote.get(), HttpStatus.OK);
    }



    // Permite guardar entidad cotizacion.
    @PostMapping()
    public ResponseEntity<QuoteSummaryEntity> saveQuotes(@RequestBody List<QuoteEntity> quoteList){
        System.out.println(quoteList.get(0));
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

    /* 
    @GetMapping("/{id}")
    public ResponseEntity<?> generatePDFquote(@PathVariable long id) {
        // Obtener la cotización por su ID desde la base de datos (o servicio correspondiente)
        Optional<QuoteEntity> checkQuote = quoteService.getQuoteById(id);
        
        if(!checkQuote.isPresent()){
            System.out.println("NO SE ENCONTRO LA COTIZACION CON ID: " + id + " \n");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La cotizacion con el ID especificado no se encuentra registrada."); 
        }

        System.out.println(checkUser);
        return ResponseEntity.ok().build();
    
         
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
    */

    @PostMapping("/{id}")
    public ResponseEntity<?> generatePDF(@PathVariable long id, @RequestBody QuoteEntity quote) {



        Optional<QuoteEntity> checkQuote = quoteService.getQuoteById(id);

        if(!checkQuote.isPresent()){
            System.out.println("NO SE ENCONTRO LA COTIZACION CON ID: " + id + " \n");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La cotizacion con el ID especificado no se encuentra registrada."); 
        }

        //FALTA VER QUE ALGUNOS PARAMETROS NO SE RECIBEN CORRECTAMENTE
        /* 
        System.out.println("amount : " + quote.getAmount() + " \n");
        System.out.println("value_square_meters : " + quote.getValueSquareMeters() + " \n");
        System.out.println("width : " + quote.getWidth() + " \n");
        System.out.println("height : " + quote.getHeight() + " \n");
        System.out.println("total_square_meters : " + quote.getTotalSquareMeters() + " \n");
        System.out.println("total_fabrics : " + quote.getTotalFabrics() + " \n");
        System.out.println("bracket_value : " + quote.getBracketValue() + " \n");
        System.out.println("cap_value : " + quote.getCapValue() + " \n");
        System.out.println("pipe_value : " + quote.getPipeValue() + " \n");
        System.out.println("counterweight_value : " + quote.getCounterweightValue() + " \n");
        System.out.println("band_value : " + quote.getBandValue() + " \n");
        System.out.println("chain_value : " + quote.getChainValue() + " \n");
        System.out.println("total_materials : " + quote.getTotalMaterials() + " \n");
        System.out.println("assembly_value : " + quote.getAssemblyValue() + " \n");
        System.out.println("installation_value : " + quote.getInstallationValue() + " \n");
        System.out.println("total_labor : " + quote.getTotalLabor() + " \n");
        System.out.println("percentage_discount : " + quote.getPercentageDiscount() + " \n");
        System.out.println("production_cost : " + quote.getProductionCost() + " \n");
        System.out.println("sale_value : " + quote.getSaleValue() + " \n");
        System.out.println("date : " + quote.getDate() + " \n");
        System.out.println("seller_sellerid : " + quote.getSeller() + " \n");
        System.out.println("curtains : " + quote.getCurtain() + " \n");
        System.out.println("pipes : " + quote.getPipe() + " \n");
        */
        
        // PARA TESTEAR
        int amount = 40;
        float value_square_meters = 12500f;
        float width = 1.00f;
        float height = 1.00f;
        float total_square_meters = 1.00f;
        float total_fabrics = 12500f;
        float bracket_value = 2500f;
        float cap_value = 1400f;
        float pipe_value = 2300f;
        float counterweight_value = 1400f;
        float band_value = 300f;
        float chain_value = 190f;
        float total_materials = 8090f;
        float assembly_value = 2000f;
        float installation_value = 5000f;
        float total_labor = 7000f;
        float percentage_discount = 0f;
        float production_cost = 27590f;
        float sale_value = 45983f;
        String dateString = "2021-09-06";
        float seller_sellerid = 3f;
        float curtains = 1f;
        float pipes = 5f;
        
        /* 
        System.out.println("amount : " + amount + " \n");
        System.out.println("value_square_meters : " + value_square_meters + " \n");
        System.out.println("width : " + width + " \n");
        System.out.println("height : " + height + " \n");
        System.out.println("total_square_meters : " + total_square_meters + " \n");
        System.out.println("total_fabrics : " + total_fabrics + " \n");
        System.out.println("bracket_value : " + bracket_value + " \n");
        System.out.println("cap_value : " + cap_value + " \n");
        System.out.println("pipe_value : " + pipe_value + " \n");
        System.out.println("counterweight_value : " + counterweight_value + " \n");
        System.out.println("band_value : " + band_value + " \n");
        System.out.println("chain_value : " + chain_value + " \n");
        System.out.println("total_materials : " + total_materials + " \n");
        System.out.println("assembly_value : " + assembly_value + " \n");
        System.out.println("installation_value : " + installation_value + " \n");
        System.out.println("total_labor : " + total_labor + " \n");
        System.out.println("percentage_discount : " + percentage_discount + " \n");
        System.out.println("production_cost : " + production_cost + " \n");
        System.out.println("sale_value : " + sale_value + " \n");
        System.out.println("date : " + dateString + " \n");
        System.out.println("seller_sellerid : " + seller_sellerid + " \n");
        System.out.println("curtains : " + curtains + " \n");
        System.out.println("pipes : " + pipes + " \n");
        */

        //Se crea el documento PDF
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);

            // Se abre el documento
            document.open();

            // Configuración de la caja
            float borderWidth = 2f;
            //float padding = 10f;
            float spacingAfter = 20f;
            BaseColor boxColor = new BaseColor(0, 153, 255); // Color azul
            BaseColor textColor = BaseColor.WHITE; // Color del texto dentro de la caja

            // Se configura el rectángulo 1 de fondo
            PdfContentByte canvas = writer.getDirectContentUnder();
            //Rectangle rect = new Rectangle(36, 750, 559, 800);
            Rectangle rect = new Rectangle(36, 765, 559, 810);
            rect.setBackgroundColor(BaseColor.LIGHT_GRAY);
            canvas.rectangle(rect);

            // Se agrega el texto "COTIZACION" dentro del rectángulo
            Font font1 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
            Paragraph paragraph = new Paragraph("COTIZACION", font1);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);

            //document.add(new Paragraph("\n\n"));

            /* 
            PdfContentByte canvas2 = writer.getDirectContent();
            // Definir las coordenadas y el tamaño de la caja
            float x = 36f; // Posición X de la esquina inferior izquierda
            float y = 400f; // Posición Y de la esquina inferior izquierda
            float width2 = 522f; // Ancho de la caja
            float height2 = 360f; // Alto de la caja
            BaseColor borderColor = BaseColor.BLACK;
            BaseColor backgroundColor = BaseColor.WHITE;
            // Dibujar el rectángulo de la caja
            canvas2.rectangle(x, y, width2, height2);
            // Establecer el color del borde
            canvas2.setColorStroke(borderColor);
            // Establecer el color del fondo
            canvas2.setColorFill(backgroundColor);
            // Dibujar el borde y el fondo
            canvas2.stroke();
            canvas2.fill();
            */

            PdfContentByte canvas2 = writer.getDirectContent();
            float x2 = 36f; // Posición X de la esquina inferior izquierda del nuevo rectángulo
            float y2 = 690f; // Posición Y de la esquina inferior izquierda del nuevo rectángulo
            float width2 = 523f; // Ancho del nuevo rectángulo
            float height2 = 75f; // Altura del nuevo rectángulo

            // Configuración de los colores y bordes del nuevo rectángulo
            BaseColor borderColor2 = BaseColor.BLACK;
            BaseColor backgroundColor2 = BaseColor.WHITE;

            // Dibujar el rectángulo
            canvas2.rectangle(x2, y2, width2, height2);
            canvas2.setColorStroke(borderColor2);
            canvas2.setColorFill(backgroundColor2);
            canvas2.stroke();
            canvas2.fill();

            // Dibujar la línea vertical en la mitad del rectángulo
            float shift = 70f; // Desplazamiento adicional hacia la derecha
            float midX2 = x2 + (width2 / 2);
            canvas2.moveTo(midX2, y2);
            canvas2.lineTo(midX2, y2 + height2);
            canvas2.stroke();

            // Definir el área de texto dentro del rectángulo izquierdo
            float padding = 5f;
            Rectangle textArea = new Rectangle(x2 + padding, y2 + padding, midX2 - padding, y2 + height2 - padding);
            ColumnText columnText = new ColumnText(canvas2);
            columnText.setSimpleColumn(textArea);

            // Agregar el texto al área de texto
            String textoIzquierdo = "Suc Brisas Oriente 1331 / Pudahuel\nAv PaJaritos 3145 / Maipu\n(+569) 95389027  (+569) 97414699 \nContacto Marcelo Civilo 95389027";
            Font font2 = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);
            Paragraph paragraph2 = new Paragraph(textoIzquierdo, font2);
            columnText.addElement(paragraph2);

            // Procesar y dibujar el contenido del área de texto
            columnText.go();
            
            // Agregar la imagen al rectángulo derecho
            float x3 = midX2 + padding; // Posición X de la esquina inferior izquierda del rectángulo derecho
            float y3 = y2 + padding; // Posición Y de la esquina inferior izquierda del rectángulo derecho
            float width3 = width2 - shift - (2 * padding); // Ancho del rectángulo derecho
            float height3 = height2 - (2 * padding); // Altura del rectángulo derecho

            String imagePath = "C:/Users/Golden Gamers/Desktop/Usach/2023/1- Primer Semestre/4- TALLER DE INGENIERIA DE SOFTWARE/civilo-g10-backend/fotopdf/roller.png"; // Ruta o URL de la imagen

            try {
                Image image = Image.getInstance(imagePath);
                image.scaleToFit(width3-15f, height3-15f);
                image.setAbsolutePosition(x3, y3+10f);
                // Agregar la imagen al documento
                document.add(image);
            } catch (MalformedURLException e) {
                // Manejo de la excepción MalformedURLException
                e.printStackTrace(); // Opcionalmente, puedes imprimir la traza de la excepción
            } catch (IOException e) {
                // Manejo de la excepción IOException
                e.printStackTrace(); // Opcionalmente, puedes imprimir la traza de la excepción
            }
            
            document.add(new Paragraph("\n\n\n\n\n"));

            // Crear una tabla con 3 columnas
            PdfPTable table = new PdfPTable(3);

            // Establecer el ancho de la tabla para que ocupe el ancho completo
            table.setWidthPercentage(100f);
            
            //Datos de las celdas de la tabla
            String[][] data = {
                {"Cliente:", "Hernan Astudillo", "Número: " + 17},
                {"Fono contacto", "+569 92297540", "Fecha: 5/6/2020"},
                {"RUT:", "", ""},
                {"Ciudad:", "Santiago", "www.rollerdeco.cl"},
                {"Pais:", "Chile", "contacto@rollerdeco.cl"},
            };

            // Agregar las celdas a la tabla
            for (String[] rowData : data) {
                for (String cellData : rowData) {
                    PdfPCell cell = new PdfPCell(new Paragraph(cellData, FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK)));
                    cell.setBackgroundColor(BaseColor.WHITE);
                    cell.setBorderColor(BaseColor.BLACK);
                    table.addCell(cell);
                }
            }



            

            

            // Agregar la tabla al documento
            document.add(table);




            // Se agregan los atributos al PDF
            //document.add(new Paragraph("  Materiales: "));
            //document.add(new Paragraph("  - Amount: " + quote.getAmount()));
            //document.add(new Paragraph("  - Value Square Meters: " + quote.getValueSquareMeters()));
            

            // Se cierra el documento
            document.close();

            // Se obtiene el contenido del PDF como bytes
            byte[] pdfBytes = outputStream.toByteArray();

            // Guardar el archivo PDF en la carpeta deseada
            // Se guarda el archivo PDF en la carpeta seleccionada

            // Se define la ruta donde se guardará el archivo PDF
            //String filePath = "C:/Users/javie/Desktop/Cotizaciones/test.pdf";
            String filePath = "C:/Users/Golden Gamers/Desktop/Usach/2023/2- Segundo Semestre/test.pdf";
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                fos.write(pdfBytes);
                System.out.println("PDF guardado exitosamente en: " + filePath);
            } catch (IOException e) {
                e.printStackTrace();
                // Manejar la excepción en caso de error al guardar el archivo
            }

            // Configurar las cabeceras de la respuesta
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "quote.pdf");

            // Devolver el PDF como respuesta
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (DocumentException e) {
            // Manejar la excepción en caso de error
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            // Cerrar el documento en caso de excepción
            document.close();
        }
    }
            

}
