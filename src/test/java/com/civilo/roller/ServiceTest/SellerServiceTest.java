package com.civilo.roller.ServiceTest;

import com.civilo.roller.Entities.RoleEntity;
import com.civilo.roller.Entities.SellerEntity;
import com.civilo.roller.repositories.SellerRepository;
import com.civilo.roller.services.SellerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class SellerServiceTest {
    @Mock
    private SellerRepository sellerRepository;

    @InjectMocks
    SellerService sellerService;

    @Test
    void saveSeller(){
        SellerEntity seller = new SellerEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20,  new RoleEntity(1L, "Cliente"), "Company", true);
        when(sellerRepository.save(seller)).thenReturn(seller);
        final SellerEntity currentResponse = sellerService.saveSeller(seller);
        assertEquals(seller,currentResponse);
    }

    @Test
    void getSellers(){
        SellerEntity seller = new SellerEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20,  new RoleEntity(1L, "Cliente"), "Company", true);
        List<SellerEntity> expectedAnswer = new ArrayList<>();
        expectedAnswer.add(seller);
        when((List<SellerEntity>) sellerRepository.findAll()).thenReturn(expectedAnswer);
        final List<SellerEntity> currentResponse = sellerService.getSellers();
        assertEquals(expectedAnswer, currentResponse);
    }

    @Test
    public void testValidateSeller() {
        String email = "seller@example.com";
        String password = "password";
        SellerEntity expectedSeller = new SellerEntity();
        expectedSeller.setEmail(email);
        expectedSeller.setPassword(password);
        when(sellerRepository.findByEmail(email)).thenReturn(expectedSeller);
        SellerEntity actualSeller = sellerService.validateSeller(email, password);
        assertEquals(expectedSeller, actualSeller);
    }

    @Test
    void testValidateSeller2() {
        String email = "seller@example.com";
        String password = "password";
        when(sellerRepository.findByEmail(email)).thenReturn(null);
        SellerEntity actualSeller = sellerService.validateSeller(email, password);
        assertNull(actualSeller);
    }
}
