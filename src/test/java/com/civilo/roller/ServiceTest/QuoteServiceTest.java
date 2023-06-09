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
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, role);
        RequestEntity requestEntity = new RequestEntity(Long.valueOf("9999"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", 1, null, user, null, null, null);
        StatusEntity status = new StatusEntity(Long.valueOf("9999"), "Status 1");
        SellerEntity seller = new SellerEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, role, "companyName", true);
        PermissionEntity permission = new PermissionEntity(Long.valueOf("9999"), "Permission 1", role);
        CurtainEntity curtain = new CurtainEntity(Long.valueOf("9999"), "Curtain 1");
        CoverageEntity coverage = new CoverageEntity(9999L, "Santiago");
        QuoteEntity quoteEntity = new QuoteEntity(Long.valueOf("9999"), 1, "Description", 1f,1f,1f,1f,1f,1f,1f,1f,1f,1f,1f,1f,1f,1f,1f,1f,1f,1f,1f,1f, new Date(), seller, curtain, null, null);

        List<QuoteEntity> quoteList = new ArrayList<>();
        quoteList.add(quoteEntity);
        when(quoteRepository.findAll()).thenReturn(quoteList);

        List<QuoteEntity> result = quoteService.getQuotes();

        verify(quoteRepository, times(1)).findAll();
        assertEquals(1, result.size());
        assertEquals("Description", result.get(0).getDescription());
    }

    @Test
    public void getQuoteByIdTest() {
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, role);
        RequestEntity requestEntity = new RequestEntity(Long.valueOf("9999"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", 1, null, user, null, null, null);
        StatusEntity status = new StatusEntity(Long.valueOf("9999"), "Status 1");
        SellerEntity seller = new SellerEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, role, "companyName", true);
        PermissionEntity permission = new PermissionEntity(Long.valueOf("9999"), "Permission 1", role);
        CurtainEntity curtain = new CurtainEntity(Long.valueOf("9999"), "Curtain 1");
        CoverageEntity coverage = new CoverageEntity(9999L, "Santiago");
        QuoteEntity quoteEntity = new QuoteEntity(Long.valueOf("9999"), 1, "Description", 10.0f, 5.0f, 5.0f, 25.0f, 20.0f, 5.0f, 2.0f, 3.0f, 1.0f, 0.5f, 2.5f, 15.0f, 8.0f, 7.0f, 30.0f, 50.0f, 10.0f, 15.0f, 60.0f, 10000f, new Date(), seller, curtain, null, null);

        when(quoteRepository.findById(anyLong())).thenReturn(Optional.of(quoteEntity));

        Optional<QuoteEntity> result = quoteService.getQuoteById(9999L);

        verify(quoteRepository, times(1)).findById(anyLong());
        assertTrue(result.isPresent());
        assertEquals("Description", result.get().getDescription());
    }

    @Test
    public void saveQuoteTest() {
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, role);
        RequestEntity requestEntity = new RequestEntity(Long.valueOf("9999"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", 1, null, user, null, null, null);
        StatusEntity status = new StatusEntity(Long.valueOf("9999"), "Status 1");
        SellerEntity seller = new SellerEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, role, "companyName", true);
        PermissionEntity permission = new PermissionEntity(Long.valueOf("9999"), "Permission 1", role);
        CurtainEntity curtain = new CurtainEntity(Long.valueOf("9999"), "Curtain 1");
        CoverageEntity coverage = new CoverageEntity(9999L, "Santiago");
        QuoteEntity quoteEntity = new QuoteEntity(Long.valueOf("9999"), 1, "Description", 10.0f, 5.0f, 5.0f, 25.0f, 20.0f, 5.0f, 2.0f, 3.0f, 1.0f, 0.5f, 2.5f, 15.0f, 8.0f, 7.0f, 30.0f, 50.0f, 10.0f, 15.0f, 60.0f, 10000f, new Date(), seller, curtain, null, null);

        when(quoteRepository.save(any(QuoteEntity.class))).thenReturn(quoteEntity);

        QuoteEntity result = quoteService.saveQuote(quoteEntity);

        verify(quoteRepository, times(1)).save(any(QuoteEntity.class));
        assertNotNull(result);
        assertEquals("Description", result.getDescription());
    }

    @Test
    public void createQuoteTest() {
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, role);
        RequestEntity requestEntity = new RequestEntity(Long.valueOf("9999"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", 1, null, user, null, null, null);
        StatusEntity status = new StatusEntity(Long.valueOf("9999"), "Status 1");
        SellerEntity seller = new SellerEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, role, "companyName", true);
        PermissionEntity permission = new PermissionEntity(Long.valueOf("9999"), "Permission 1", role);
        CurtainEntity curtain = new CurtainEntity(Long.valueOf("9999"), "Curtain 1");
        CoverageEntity coverage = new CoverageEntity(9999L, "Santiago");
        QuoteEntity quoteEntity = new QuoteEntity(Long.valueOf("9999"), 1, "Description", 10.0f, 5.0f, 5.0f, 25.0f, 20.0f, 5.0f, 2.0f, 3.0f, 1.0f, 0.5f, 2.5f, 15.0f, 8.0f, 7.0f, 30.0f, 50.0f, 10.0f, 15.0f, 60.0f, 10000f, new Date(), seller, curtain, null, null);

        when(quoteRepository.save(any(QuoteEntity.class))).thenReturn(quoteEntity);

        QuoteEntity result = quoteService.createQuote(quoteEntity);

        verify(quoteRepository, times(1)).save(any(QuoteEntity.class));
        assertNotNull(result);
        assertEquals("Description", result.getDescription());
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
}
