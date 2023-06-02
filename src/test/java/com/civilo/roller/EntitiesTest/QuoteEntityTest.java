package com.civilo.roller.EntitiesTest;

import com.civilo.roller.Entities.*;
import com.civilo.roller.services.RequestService;
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
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class QuoteEntityTest {
    @InjectMocks
    private QuoteEntity quoteEntity;

    @Test
    public void quoteEntityTest() {
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, role);
        PermissionEntity permission = new PermissionEntity(Long.valueOf("9999"), "Permission 1", role);
        SellerEntity seller = new SellerEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, role, "companyName", true);
        CurtainEntity curtain = new CurtainEntity(Long.valueOf("9999"), "Curtain 1");

        quoteEntity.setQuoteID(Long.valueOf("9999"));
        quoteEntity.setAmount(1);
        quoteEntity.setDescription("Description");
        quoteEntity.setValueSquareMeters(10.0f);
        quoteEntity.setWidth(5.0f);
        quoteEntity.setHeight(2.5f);
        quoteEntity.setTotalSquareMeters(12.5f);
        quoteEntity.setTotalFabrics(15.0f);
        quoteEntity.setBracket(1.0f);
        quoteEntity.setCap(2.0f);
        quoteEntity.setPipe(0.5f);
        quoteEntity.setCounterweight(0.5f);
        quoteEntity.setBand(0.2f);
        quoteEntity.setChain(0.3f);
        quoteEntity.setTotalMaterials(4.0f);
        quoteEntity.setAssembly(10.0f);
        quoteEntity.setInstallation(5.0f);
        quoteEntity.setTotalLabor(15.0f);
        quoteEntity.setProductionCost(100.0f);
        quoteEntity.setSaleValue(150.0f);
        quoteEntity.setPercentageDiscount(0.1f);
        quoteEntity.setIVA(0.19f);
        quoteEntity.setTotal(135.0f);
        quoteEntity.setSeller(seller);
        quoteEntity.setCurtain(curtain);

        assertEquals(Long.valueOf("9999"), quoteEntity.getQuoteID());
        assertEquals(1, quoteEntity.getAmount());
        assertEquals("Description", quoteEntity.getDescription());
        assertEquals(10.0f, quoteEntity.getValueSquareMeters());
        assertEquals(5.0f, quoteEntity.getWidth());
        assertEquals(2.5f, quoteEntity.getHeight());
        assertEquals(12.5f, quoteEntity.getTotalSquareMeters());
        assertEquals(15.0f, quoteEntity.getTotalFabrics());
        assertEquals(1.0f, quoteEntity.getBracket());
        assertEquals(2.0f, quoteEntity.getCap());
        assertEquals(0.5f, quoteEntity.getPipe());
        assertEquals(0.5f, quoteEntity.getCounterweight());
        assertEquals(0.2f, quoteEntity.getBand());
        assertEquals(0.3f, quoteEntity.getChain());
        assertEquals(4.0f, quoteEntity.getTotalMaterials());
        assertEquals(10.0f, quoteEntity.getAssembly());
        assertEquals(5.0f, quoteEntity.getInstallation());
        assertEquals(15.0f, quoteEntity.getTotalLabor());
        assertEquals(100.0f, quoteEntity.getProductionCost());
        assertEquals(150.0f, quoteEntity.getSaleValue());
        assertEquals(0.1f, quoteEntity.getPercentageDiscount());
        assertEquals(0.19f, quoteEntity.getIVA());
        assertEquals(135.0f, quoteEntity.getTotal());
        assertEquals(seller, quoteEntity.getSeller());
        assertEquals(curtain, quoteEntity.getCurtain());
    }



}
