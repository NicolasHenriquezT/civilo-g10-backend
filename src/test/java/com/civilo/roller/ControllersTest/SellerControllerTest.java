package com.civilo.roller.ControllersTest;

import com.civilo.roller.Entities.RoleEntity;
import com.civilo.roller.Entities.SellerEntity;
import com.civilo.roller.controllers.SellerController;
import com.civilo.roller.services.SellerService;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class SellerControllerTest {
    @Mock
    private SellerService sellerService;
    @InjectMocks
    private SellerController sellerController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetSellers() {
        List<SellerEntity> expectedSellers = new ArrayList<>();
        expectedSellers.add(new SellerEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, new RoleEntity(1L, "Cliente"), "Company", true));
        expectedSellers.add(new SellerEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20,  new RoleEntity(1L, "Cliente"), "Company", true));
        Mockito.when(sellerService.getSellers()).thenReturn(expectedSellers);
        List<SellerEntity> actualSeller = sellerController.getSellers();
        assertEquals(expectedSellers, actualSeller);
    }

    @Test
    void testSaveSeller() {
        SellerEntity expectedSeller = new SellerEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20,  new RoleEntity(1L, "Cliente"), "Company", true);
        Mockito.when(sellerService.saveSeller(Mockito.any(SellerEntity.class))).thenReturn(expectedSeller);
        SellerEntity actualSeller = sellerController.saveSeller(new SellerEntity());
        assertEquals(expectedSeller, actualSeller);
    }
}
