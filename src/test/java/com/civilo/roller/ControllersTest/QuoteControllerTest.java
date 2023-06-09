package com.civilo.roller.ControllersTest;

import com.civilo.roller.Entities.QuoteEntity;
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
        expectedQuotes.add(new QuoteEntity(Long.valueOf("9999"), 1, "Description", 10.0f, 5.0f, 5.0f, 25.0f, 20.0f, 5.0f, 2.0f, 3.0f, 1.0f, 0.5f, 2.5f, 15.0f, 8.0f, 7.0f, 30.0f, 50.0f, 10.0f, 15.0f, 60.0f, 10000f, new Date(), null, null, null, null));
        expectedQuotes.add(new QuoteEntity(Long.valueOf("9998"), 1, "Description", 10.0f, 5.0f, 5.0f, 25.0f, 20.0f, 5.0f, 2.0f, 3.0f, 1.0f, 0.5f, 2.5f, 15.0f, 8.0f, 7.0f, 30.0f, 50.0f, 10.0f, 15.0f, 60.0f, 10000f, new Date(), null, null, null, null));
        Mockito.when(quoteService.getQuotes()).thenReturn(expectedQuotes);
        List<QuoteEntity> actualQuotes = quoteController.getQuotes();
        assertEquals(expectedQuotes, actualQuotes);
    }

    @Test
    void saveQuote() {
        QuoteEntity expectedQuote = new QuoteEntity(Long.valueOf("9999"), 1, "Description", 10.0f, 5.0f, 5.0f, 25.0f, 20.0f, 5.0f, 2.0f, 3.0f, 1.0f, 0.5f, 2.5f, 15.0f, 8.0f, 7.0f, 30.0f, 50.0f, 10.0f, 15.0f, 60.0f, 10000f, new Date(), null, null, null, null);
        QuoteEntity actualQuote = quoteController.saveQuote(expectedQuote);
        assertEquals(null, actualQuote);
    }

    @Test
    public void testDeleteQuote() {
        quoteService.deleteQuotes();
        verify(quoteRepository, times(0)).deleteAll();
    }
}
