package com.civilo.roller.EntitiesTest;

import com.civilo.roller.Entities.QuoteEntity;
import com.civilo.roller.Entities.SellerEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class QuoteEntityTest {
    @Test
    void testQuoteEntity() {
        SellerEntity seller = Mockito.mock(SellerEntity.class);
        QuoteEntity quote = new QuoteEntity();
        quote.setQuoteID(1L);
        quote.setProductName("Producto");
        quote.setAmount(10);
        quote.setValue(100);
        quote.setDescription("Descripción");
        quote.setCommission(5.0f);
        quote.setSeller(seller);
        assertEquals(1L, quote.getQuoteID());
        assertEquals("Producto", quote.getProductName());
        assertEquals(10, quote.getAmount());
        assertEquals(100, quote.getValue());
        assertEquals("Descripción", quote.getDescription());
        assertEquals(5.0f, quote.getCommission());
        assertEquals(seller, quote.getSeller());
    }
}
