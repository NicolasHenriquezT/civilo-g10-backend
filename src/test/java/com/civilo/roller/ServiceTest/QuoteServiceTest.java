package com.civilo.roller.ServiceTest;

import com.civilo.roller.Entities.*;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class QuoteServiceTest {
    @Mock
    private QuoteRepository quoteRepository;

    @InjectMocks
    private QuoteService quoteService;

    @Test
    public void getQuotesTest() {
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role);
        RequestEntity requestEntity = new RequestEntity(Long.valueOf("9999"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", 1, null, user, null, null, null);
        StatusEntity status = new StatusEntity(Long.valueOf("9999"), "Status 1");
        SellerEntity seller = new SellerEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role, "companyName", true, "rut", "banco", "cuenta", 1);
        PermissionEntity permission = new PermissionEntity(Long.valueOf("9999"), "Permission 1", role);
        CurtainEntity curtain = new CurtainEntity(Long.valueOf("9999"), "Curtain 1");
        CoverageEntity coverage = new CoverageEntity(9999L, "Santiago");
        QuoteEntity quoteEntity = new QuoteEntity(1L, 1, 12500f, 1f, 1f, 1f, 12500f, 2300f, 1500f, 900f, 300f, 600f, 190f, 8090f, 2000f, 5000f, 7000f, 0f, 44000f, 77000f, null, null, null, null, null);

        List<QuoteEntity> quoteList = new ArrayList<>();
        quoteList.add(quoteEntity);
        when(quoteRepository.findAll()).thenReturn(quoteList);

        List<QuoteEntity> result = quoteService.getQuotes();

        verify(quoteRepository, times(1)).findAll();
        assertEquals(1, result.size());
    }

    @Test
    public void getQuoteByIdTest() {
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role);
        RequestEntity requestEntity = new RequestEntity(Long.valueOf("9999"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", 1, null, user, null, null, null);
        StatusEntity status = new StatusEntity(Long.valueOf("9999"), "Status 1");
        SellerEntity seller = new SellerEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role, "companyName", true, "rut", "banco", "cuenta", 1);
        PermissionEntity permission = new PermissionEntity(Long.valueOf("9999"), "Permission 1", role);
        CurtainEntity curtain = new CurtainEntity(Long.valueOf("9999"), "Curtain 1");
        CoverageEntity coverage = new CoverageEntity(9999L, "Santiago");
        QuoteEntity quoteEntity = new QuoteEntity(1L, 1, 12500f, 1f, 1f, 1f, 12500f, 2300f, 1500f, 900f, 300f, 600f, 190f, 8090f, 2000f, 5000f, 7000f, 0f, 44000f, 77000f, null, null, null, null, null);

        when(quoteRepository.findById(anyLong())).thenReturn(Optional.of(quoteEntity));

        Optional<QuoteEntity> result = quoteService.getQuoteById(9999L);

        verify(quoteRepository, times(1)).findById(anyLong());
        assertTrue(result.isPresent());
    }

    @Test
    public void saveQuoteTest() {
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role);
        RequestEntity requestEntity = new RequestEntity(Long.valueOf("9999"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", 1, null, user, null, null, null);
        StatusEntity status = new StatusEntity(Long.valueOf("9999"), "Status 1");
        SellerEntity seller = new SellerEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role, "companyName", true, "rut", "banco", "cuenta", 1);
        PermissionEntity permission = new PermissionEntity(Long.valueOf("9999"), "Permission 1", role);
        CurtainEntity curtain = new CurtainEntity(Long.valueOf("9999"), "Curtain 1");
        CoverageEntity coverage = new CoverageEntity(9999L, "Santiago");
        QuoteEntity quoteEntity = new QuoteEntity(1L, 1, 12500f, 1f, 1f, 1f, 12500f, 2300f, 1500f, 900f, 300f, 600f, 190f, 8090f, 2000f, 5000f, 7000f, 0f, 44000f, 77000f, null, null, null, null, null);

        when(quoteRepository.save(any(QuoteEntity.class))).thenReturn(quoteEntity);

        QuoteEntity result = quoteService.saveQuote(quoteEntity);

        verify(quoteRepository, times(1)).save(any(QuoteEntity.class));
        assertNotNull(result);
    }

    @Test
    public void createQuoteTest() {
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role);
        RequestEntity requestEntity = new RequestEntity(Long.valueOf("9999"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", 1, null, user, null, null, null);
        StatusEntity status = new StatusEntity(Long.valueOf("9999"), "Status 1");
        SellerEntity seller = new SellerEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role, "companyName", true, "rut", "banco", "cuenta", 1);
        PermissionEntity permission = new PermissionEntity(Long.valueOf("9999"), "Permission 1", role);
        CurtainEntity curtain = new CurtainEntity(Long.valueOf("9999"), "Curtain 1");
        CoverageEntity coverage = new CoverageEntity(9999L, "Santiago");
        QuoteEntity quoteEntity = new QuoteEntity(1L, 1, 12500f, 1f, 1f, 1f, 12500f, 2300f, 1500f, 900f, 300f, 600f, 190f, 8090f, 2000f, 5000f, 7000f, 0f, 44000f, 77000f, null, null, null, null, null);

        when(quoteRepository.save(any(QuoteEntity.class))).thenReturn(quoteEntity);

        QuoteEntity result = quoteService.createQuote(quoteEntity);

        verify(quoteRepository, times(1)).save(any(QuoteEntity.class));
        assertNotNull(result);
    }

    @Test
    public void deleteQuotesTest() {
        quoteService.deleteQuotes();
        verify(quoteRepository, times(1)).deleteAll();
    }

    @Test
    public void deleteQuoteByIdTest() {
        Long id = Long.valueOf("9999");
        quoteService.deleteQuoteById(id);
        verify(quoteRepository, times(1)).deleteById(id);
    }

    @Test
    public void existsQuoteByIdTest() {
        Long id = Long.valueOf("9999");
        when(quoteRepository.findById(id)).thenReturn(Optional.of(new QuoteEntity()));
        boolean result = quoteService.existsQuoteById(id);
        verify(quoteRepository, times(1)).findById(id);
        assertTrue(result);
    }

    @Test
    public void calculation_UpdatesQuoteEntityCorrectly() {
        QuoteEntity quote = new QuoteEntity();
        quote.setHeight(1f);
        quote.setWidth(1f);
        quote.setValueSquareMeters(12500f);
        quote.setAmount(1);
        quote.setBracketValue(2500f);
        quote.setCapValue(1400f);
        quote.setPipeValue(2300f);
        quote.setCounterweightValue(1400f);
        quote.setBandValue(300f);
        quote.setChainValue(190f);
        quote.setAssemblyValue(2000f);
        quote.setInstallationValue(5000f);
        QuoteService quoteService = new QuoteService();
        quoteService.calculation(quote);
        assertNotNull(quote.getDate());
        assertEquals(2f, quote.getTotalSquareMeters());
        assertEquals(12500f, quote.getTotalFabrics());
        assertEquals(8090f, quote.getTotalMaterials());
        assertEquals(7000f, quote.getTotalLabor());
        assertEquals(27590f, quote.getProductionCost());
        assertEquals(45984f, quote.getSaleValue());
    }

    @Test
    public void createQuotes_SavesQuoteEntitiesToRepository() {
        List<QuoteEntity> quoteList = new ArrayList<>();
        quoteList.add(new QuoteEntity());
        quoteList.add(new QuoteEntity());
        quoteList.add(new QuoteEntity());
        quoteService.createQuotes(quoteList);
        Mockito.verify(quoteRepository, times(3)).save(Mockito.any(QuoteEntity.class));
    }
}
