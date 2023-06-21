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

        System.out.println("nombre del cliente: " + quote.get().getRequestEntity().getUser().getName() + "\n");
        System.out.println("telefono del cliente: " + quote.get().getRequestEntity().getUser().getPhoneNumber() + "\n");




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


    @PostMapping("/{id}")
    public ResponseEntity<?> generatePDF(@RequestBody SellerEntity seller) {

        

        //System.out.println("PRINT DE PDFFFFFFFFFFFFFFFFFFFFFFFFFFFF \n\n\n");

        //System.out.println(quote);

        //{}
        //id de solicitud, idvendedor
        //filtrar en 





        // Se revisa si existe la cotizacion
        Optional<QuoteEntity> checkQuote = quoteService.getQuoteById(id);

        if(!checkQuote.isPresent()){
            System.out.println("NO SE ENCONTRO LA COTIZACION CON ID: " + id + " \n");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La cotizacion con el ID especificado no se encuentra registrada."); 
        }

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
            Paragraph paragraph = new Paragraph("COTIZACIÓN", font1);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);

            // Se configura el rectangulo 2
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
            String textoIzquierdo = "Suc Brisas Oriente 1331 / Pudahuel\nAv Pajaritos 3145 / Maipú\n(+569) 95389027  (+569) 97414699 \nContacto Marcelo Civilo 95389027";
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
            //String imagePath = "C:/Users/javie/Cotizaciones/civilo-g10-backend/fotopdf/roller.png"; // Ruta o URL de la imagen

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
                {"Cliente:", quote.getRequestEntity().getUser().getName(), "Número: " + quote.getRequestEntity().getRequestID()},
                {"Fono contacto", quote.getRequestEntity().getUser().getPhoneNumber(), "Fecha: " + quote.getRequestEntity().getAdmissionDate()},
                //{"RUT:", "", ""},
                {"Comuna:", quote.getRequestEntity().getCoverage().getCommune(), "www.rollerdeco.cl"},
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


            /* 
            [  {"amount":50,"valueSquareMeters":50,"width":50,"height":50,"bracketValue":50,"capValue":50,"counterweightValue":50,"bandValue":50,"chainValue":50,"pipe":{"pipeID":5,"pipeName":"Tubo 38 mm"},"pipeValue":50,"assemblyValue":"50","installationValue":"50","description":"","totalSquareMeters":null,"totalFabrics":null,"totalMaterials":null,"totalLabor":null,"productionCost":null,"saleValue":null,"percentageDiscount":0,"iva":19,"total":null,"date":null,"seller":{"userID":3,"name":"Vendedor","surname":"1","email":"vendedor@gmail.com","password":"vendedor","phoneNumber":"0 1234 5678","commune":"Petorca","birthDate":"2000-04-10","age":23,"startTime":null,"endTime":null,"role":{"roleID":3,"accountType":"Vendedor"},"companyName":"Compañia 1","disponibility":true,"rut":null,"bank":null,"bankAccountType":null,"bankAccountNumber":null,"coverageID":[8,110,186],"quoteEntities":[]},"curtain":{"curtainID":1,"curtainType":"Roller Blackout"},"currentIVA":null,"requestEntity":{"requestID":1,"description":"Texto prueba","deadline":"2023-06-07","admissionDate":"2023-06-02","closingDate":null,"reason":null,"sellerId":3,"userID":[],"user":{"userID":4,"name":"Cliente","surname":"1","email":"cliente@gmail.com","password":"cliente","phoneNumber":"0 1234 5678","commune":"Hualpén","birthDate":"2002-01-26","age":21,"startTime":null,"endTime":null,"role":{"roleID":4,"accountType":"Cliente"}},"coverage":{"coverageID":251,"commune":"Quinta Normal"},"curtain":{"curtainID":1,"curtainType":"Roller Blackout"},"status":{"statusID":2,"statusName":"Asignada"}}},
            
               {"amount":100,"valueSquareMeters":100,"width":100,"height":100,"bracketValue":10,"capValue":100,"counterweightValue":100,"bandValue":100,"chainValue":100,"pipe":{"pipeID":5,"pipeName":"Tubo 38 mm"},"pipeValue":100,"assemblyValue":"100","installationValue":"100","description":"","totalSquareMeters":null,"totalFabrics":null,"totalMaterials":null,"totalLabor":null,"productionCost":null,"saleValue":null,"percentageDiscount":0,"iva":19,"total":null,"date":null,"seller":{"userID":3,"name":"Vendedor","surname":"1","email":"vendedor@gmail.com","password":"vendedor","phoneNumber":"0 1234 5678","commune":"Petorca","birthDate":"2000-04-10","age":23,"startTime":null,"endTime":null,"role":{"roleID":3,"accountType":"Vendedor"},"companyName":"Compañia 1","disponibility":true,"rut":null,"bank":null,"bankAccountType":null,"bankAccountNumber":null,"coverageID":[8,110,186],"quoteEntities":[]},"curtain":{"curtainID":2,"curtainType":"Roller Screen"},"currentIVA":null,"requestEntity":{"requestID":1,"description":"Texto prueba","deadline":"2023-06-07","admissionDate":"2023-06-02","closingDate":null,"reason":null,"sellerId":3,"userID":[],"user":{"userID":4,"name":"Cliente","surname":"1","email":"cliente@gmail.com","password":"cliente","phoneNumber":"0 1234 5678","commune":"Hualpén","birthDate":"2002-01-26","age":21,"startTime":null,"endTime":null,"role":{"roleID":4,"accountType":"Cliente"}},"coverage":{"coverageID":251,"commune":"Quinta Normal"},"curtain":{"curtainID":1,"curtainType":"Roller Blackout"},"status":{"statusID":2,"statusName":"Asignada"}}},
               
               {"amount":null,"valueSquareMeters":null,"width":null,"height":null,"bracketValue":null,"capValue":null,"counterweightValue":null,"bandValue":null,"chainValue":null,"pipe":null,"pipeValue":null,"assemblyValue":null,"installationValue":null,"description":"","totalSquareMeters":null,"totalFabrics":null,"totalMaterials":null,"totalLabor":null,"productionCost":null,"saleValue":null,"percentageDiscount":0,"iva":19,"total":null,"date":null,"seller":{"userID":3,"name":"Vendedor","surname":"1","email":"vendedor@gmail.com","password":"vendedor","phoneNumber":"0 1234 5678","commune":"Petorca","birthDate":"2000-04-10","age":23,"startTime":null,"endTime":null,"role":{"roleID":3,"accountType":"Vendedor"},"companyName":"Compañia 1","disponibility":true,"rut":null,"bank":null,"bankAccountType":null,"bankAccountNumber":null,"coverageID":[8,110,186],"quoteEntities":[]},"curtain":{"curtainID":3,"curtainType":"Duo Blackout"},"currentIVA":null,"requestEntity":{"requestID":1,"description":"Texto prueba","deadline":"2023-06-07","admissionDate":"2023-06-02","closingDate":null,"reason":null,"sellerId":3,"userID":[],"user":{"userID":4,"name":"Cliente","surname":"1","email":"cliente@gmail.com","password":"cliente","phoneNumber":"0 1234 5678","commune":"Hualpén","birthDate":"2002-01-26","age":21,"startTime":null,"endTime":null,"role":{"roleID":4,"accountType":"Cliente"}},"coverage":{"coverageID":251,"commune":"Quinta Normal"},"curtain":{"curtainID":1,"curtainType":"Roller Blackout"},"status":{"statusID":2,"statusName":"Asignada"}}},
               
               {"amount":null,"valueSquareMeters":null,"width":null,"height":null,"bracketValue":null,"capValue":null,"counterweightValue":null,"bandValue":null,"chainValue":null,"pipe":null,"pipeValue":null,"assemblyValue":null,"installationValue":null,"description":"","totalSquareMeters":null,"totalFabrics":null,"totalMaterials":null,"totalLabor":null,"productionCost":null,"saleValue":null,"percentageDiscount":0,"iva":19,"total":null,"date":null,"seller":{"userID":3,"name":"Vendedor","surname":"1","email":"vendedor@gmail.com","password":"vendedor","phoneNumber":"0 1234 5678","commune":"Petorca","birthDate":"2000-04-10","age":23,"startTime":null,"endTime":null,"role":{"roleID":3,"accountType":"Vendedor"},"companyName":"Compañia 1","disponibility":true,"rut":null,"bank":null,"bankAccountType":null,"bankAccountNumber":null,"coverageID":[8,110,186],"quoteEntities":[]},"curtain":{"curtainID":4,"curtainType":"Duo Transparente"},"currentIVA":null,"requestEntity":{"requestID":1,"description":"Texto prueba","deadline":"2023-06-07","admissionDate":"2023-06-02","closingDate":null,"reason":null,"sellerId":3,"userID":[],"user":{"userID":4,"name":"Cliente","surname":"1","email":"cliente@gmail.com","password":"cliente","phoneNumber":"0 1234 5678","commune":"Hualpén","birthDate":"2002-01-26","age":21,"startTime":null,"endTime":null,"role":{"roleID":4,"accountType":"Cliente"}},"coverage":{"coverageID":251,"commune":"Quinta Normal"},"curtain":{"curtainID":1,"curtainType":"Roller Blackout"},"status":{"statusID":2,"statusName":"Asignada"}}}]
            */


































































            document.add(new Paragraph("\n"));
            
            // Crear una tabla con 8 columnas
            PdfPTable table2 = new PdfPTable(8);

            // Establecer el ancho de la tabla para que ocupe el ancho completo
            table2.setWidthPercentage(100f);

            // Agregar celdas a la tabla

            // Primera columna
            PdfPCell cell1 = new PdfPCell(new Paragraph("Tipo", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.BLACK)));
            cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell1.setBorderColor(BaseColor.BLACK);
            table2.addCell(cell1);

            // Segunda columna
            PdfPCell cell2 = new PdfPCell(new Paragraph("Ancho (mt)", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.BLACK)));
            cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell2.setBorderColor(BaseColor.BLACK);
            table2.addCell(cell2);

            // Tercera columna
            PdfPCell cell3 = new PdfPCell(new Paragraph("Alto (mt)", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.BLACK)));
            cell3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell3.setBorderColor(BaseColor.BLACK);
            table2.addCell(cell3);

            // Cuarta columna
            PdfPCell cell4 = new PdfPCell(new Paragraph("Tela", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.BLACK)));
            cell4.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell4.setBorderColor(BaseColor.BLACK);
            table2.addCell(cell4);

            // Quinta columna
            PdfPCell cell5 = new PdfPCell(new Paragraph("Unidades", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.BLACK)));
            cell5.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell5.setBorderColor(BaseColor.BLACK);
            table2.addCell(cell5);

            // Sexta columna
            PdfPCell cell6 = new PdfPCell(new Paragraph("Nota", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.BLACK)));
            cell6.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell6.setBorderColor(BaseColor.BLACK);
            table2.addCell(cell6);

            // Septima columna
            PdfPCell cell7 = new PdfPCell(new Paragraph("Valor Unitario", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.BLACK)));
            cell7.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell7.setBorderColor(BaseColor.BLACK);
            table2.addCell(cell7);

            // Octava columna
            PdfPCell cell8 = new PdfPCell(new Paragraph("Total", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.BLACK)));
            cell8.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell8.setBorderColor(BaseColor.BLACK);
            table2.addCell(cell8);

            // Datos para las filas (ejemplo)
            List<String[]> data2 = new ArrayList<>();
            data2.add(new String[]{"Tipo 1", "Ancho 1", "Alto 1", "Tela 1", "Unidades 1", "Nota 1", "Valor Unitario 1", "Total 1"});
            data2.add(new String[]{"Tipo 2", "Ancho 2", "Alto 2", "Tela 2", "Unidades 2", "Nota 2", "Valor Unitario 2", "Total 2"});
            data2.add(new String[]{"Tipo 3", "Ancho 3", "Alto 3", "Tela 3", "Unidades 3", "Nota 3", "Valor Unitario 3", "Total 3"});

            // Agregar las filas a la tabla
            for (String[] row : data2) {
                for (String value : row) {
                    PdfPCell cellx = new PdfPCell(new Paragraph(value, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.BLACK)));
                    cellx.setBorderColor(BaseColor.BLACK);
                    table2.addCell(cellx);
                }
            }

            // Agregar la tabla al documento
            document.add(table2);

            //---------------------------

            PdfPTable table3 = new PdfPTable(2);
            table3.setWidthPercentage(100);

            // Columna 1
            PdfPCell cell19 = new PdfPCell(new Paragraph("Datos transferencia", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.BLACK)));
            cell19.setRowspan(6); // Fusionar 3 filas en la columna 1
            //cell19.setPercentageWidth(75f);
            table3.addCell(cell19);

            // Columna 2
            PdfPCell cell20 = new PdfPCell(new Paragraph("Fila 1, Columna 2"));
            //PdfPCell cell20;
            //table3.addCell(cell20);

            // Crear la tabla anidada con 2 columnas
            PdfPTable nestedTable = new PdfPTable(2);
            nestedTable.setWidthPercentage(101f);

            // Agregar celdas a la tabla anidada
            nestedTable.addCell(new PdfPCell(new Paragraph("Subtotal", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.BLACK))));
            nestedTable.addCell(new PdfPCell(new Paragraph("$ Subtotal", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.BLACK))));
            nestedTable.addCell(new PdfPCell(new Paragraph("Descuento", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.BLACK))));
            nestedTable.addCell(new PdfPCell(new Paragraph("$ Descuento", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.BLACK))));
            nestedTable.addCell(new PdfPCell(new Paragraph("Neto", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.BLACK))));
            nestedTable.addCell(new PdfPCell(new Paragraph("$ Neto", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.BLACK))));
            nestedTable.addCell(new PdfPCell(new Paragraph("IVA", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.BLACK))));
            nestedTable.addCell(new PdfPCell(new Paragraph("$ IVA", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.BLACK))));
            nestedTable.addCell(new PdfPCell(new Paragraph("TOTAL", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.BLACK))));
            nestedTable.addCell(new PdfPCell(new Paragraph("$ TOTAL", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.BLACK))));
            nestedTable.addCell(new PdfPCell(new Paragraph("Incluye instalación?", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.BLACK))));
            nestedTable.addCell(new PdfPCell(new Paragraph("Si / No", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.BLACK))));

            cell20.addElement(nestedTable);
            table3.addCell(cell20);

            // Omitir la celda de la columna 1 para la segunda fila
            PdfPCell emptyCell = new PdfPCell();
            emptyCell.setBorder(Rectangle.NO_BORDER);
            table3.addCell(emptyCell);

            // Agregar una celda más grande en la segunda fila, columna 2
            PdfPCell cell21 = new PdfPCell(new Paragraph("Comentarios:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.BLACK)));
            cell21.setColspan(2); // Fusionar 2 columnas en la celda
            table3.addCell(cell21);


            // Agregar la tabla al documento
            document.add(table3);


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
