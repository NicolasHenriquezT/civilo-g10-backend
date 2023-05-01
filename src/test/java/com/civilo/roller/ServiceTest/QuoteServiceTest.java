package com.civilo.roller.ServiceTest;

import com.civilo.roller.Entities.QuoteEntity;
import com.civilo.roller.repositories.QuoteRepository;
import com.civilo.roller.services.QuoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class QuoteServiceTest {
    @Mock
    private QuoteRepository quoteRepository;

    @InjectMocks
    QuoteService quoteService;

    @Test
    void saveQuote(){
        QuoteEntity quote = new QuoteEntity(Long.valueOf("9999"), "Producto", 9999, 1111, "Test", Float.valueOf("1.0"), null);
        Mockito.when(quoteRepository.save(quote)).thenReturn(quote);
        final QuoteEntity currentResponse = quoteService.saveQuote(quote);
        assertEquals(quote,currentResponse);
    }

    @Test
    void getQuotes(){
        QuoteEntity quote = new QuoteEntity(Long.valueOf("9999"), "Producto", 9999, 1111, "Test", Float.valueOf("1.0"), null);
        List<QuoteEntity> expectedAnswer = new ArrayList<>();
        expectedAnswer.add(quote);
        Mockito.when((List<QuoteEntity>) quoteRepository.findAll()).thenReturn(expectedAnswer);
        final List<QuoteEntity> currentResponse = quoteService.getQuotes();
        assertEquals(expectedAnswer, currentResponse);
    }
}
