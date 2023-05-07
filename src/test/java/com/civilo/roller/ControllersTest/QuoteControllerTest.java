package com.civilo.roller.ControllersTest;

import com.civilo.roller.Entities.QuoteEntity;
import com.civilo.roller.controllers.QuoteController;
import com.civilo.roller.services.QuoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuoteControllerTest {
    @Mock
    private QuoteService quoteService;
    @InjectMocks
    private QuoteController quoteController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetQuotes() {
        List<QuoteEntity> expectedQuotes = new ArrayList<>();
        expectedQuotes.add(new QuoteEntity(Long.valueOf("1"), "Producto", 9999, 1111, "Test", Float.valueOf("1.0"), null));
        expectedQuotes.add(new QuoteEntity(Long.valueOf("2"), "Producto", 9999, 1111, "Test", Float.valueOf("1.0"), null));
        Mockito.when(quoteService.getQuotes()).thenReturn(expectedQuotes);
        List<QuoteEntity> actualQuotes = quoteController.getQuotes();
        assertEquals(expectedQuotes, actualQuotes);
    }


}
