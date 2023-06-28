package com.civilo.roller.ControllersTest;

import com.civilo.roller.Entities.QuoteEntity;
import com.civilo.roller.Entities.QuoteSummaryEntity;
import com.civilo.roller.Entities.SellerEntity;
import com.civilo.roller.controllers.QuoteController;
import com.civilo.roller.repositories.QuoteRepository;
import com.civilo.roller.services.QuoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class QuoteControllerTest {
    @Mock
    private QuoteService quoteService;
    @InjectMocks
    private QuoteController quoteController;

    @Mock
    private QuoteRepository quoteRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getQuotes() {
        List<QuoteEntity> expectedQuotes = new ArrayList<>();
        expectedQuotes.add(new QuoteEntity(1L, 1, 12500f, 1f, 1f, 1f, 12500f, 2300f, 1500f, 900f, 300f, 600f, 190f, 8090f, 2000f, 5000f, 7000f, 0f, 44000f, 77000f, null, null, null, null, null, null, null));
        expectedQuotes.add(new QuoteEntity(2L, 1, 10.0f, 5.0f, 5.0f, 25.0f, 20.0f, 5.0f, 2.0f, 3.0f, 1.0f, 0.5f, 2.5f, 15.0f, 8.0f, 7.0f, 30.0f, 50.0f, 10.0f, 77000f, null, null, null, null, null, null, null));
        Mockito.when(quoteService.getQuotes()).thenReturn(expectedQuotes);
        List<QuoteEntity> actualQuotes = quoteController.getQuotes();
        assertEquals(expectedQuotes, actualQuotes);
    }

    @Test
    public void testDeleteQuote() {
        quoteService.deleteQuotes();
        verify(quoteRepository, times(0)).deleteAll();
    }

    @Test
    public void testGeneratePDF1() {
        Long id = 1L;
        SellerEntity seller = new SellerEntity(1L, null, null, null, null, null, null, null, null, 0, null, null, null, null, true, null, null, 0);

        List<QuoteSummaryEntity> listSummary = new ArrayList<>();
        QuoteSummaryEntity summarySelected = new QuoteSummaryEntity(2L, null, 0, 0, 0, 0, 0, 0, null, seller, null);
        listSummary.add(summarySelected);
        List<QuoteEntity> listQuotes = new ArrayList<>();
        listQuotes.add(new QuoteEntity(1L, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, null, seller, null, null, null, null, null));
        when(quoteService.listQuoteSummary(anyLong())).thenReturn(listSummary);
        when(quoteService.findQuoteSummary(anyList(), anyLong(), anyLong())).thenReturn(new QuoteSummaryEntity());
        when(quoteService.listQuotes(any(QuoteSummaryEntity.class), anyLong(), anyLong())).thenReturn(new ArrayList<>());

        ResponseEntity<?> response = quoteController.generatePDF(id, seller);

        assertEquals("No se encontro un resumen de cotizacion para la solicitud seleccionada.", response.getBody());
    }
}
