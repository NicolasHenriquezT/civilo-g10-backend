package com.civilo.roller.ControllersTest;

import com.civilo.roller.Entities.ProfitMarginEntity;
import com.civilo.roller.controllers.ProfitMarginController;
import com.civilo.roller.repositories.ProfitMarginRepository;
import com.civilo.roller.services.ProfitMarginService;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class ProfitMarginControllerTest {
    @Mock
    private ProfitMarginService profitMarginService;
    @InjectMocks
    private ProfitMarginController profitMarginController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProfitMargins() {
        List<ProfitMarginEntity> expectedProfitMargins = new ArrayList<>();
        expectedProfitMargins.add(new ProfitMarginEntity(1L, 1, 1));
        expectedProfitMargins.add(new ProfitMarginEntity(2L, 1, 1));
        Mockito.when(profitMarginService.getProfitMargins()).thenReturn(expectedProfitMargins);
        List<ProfitMarginEntity> actualProfitMargin = profitMarginController.getProfitMargins();
        assertEquals(expectedProfitMargins, actualProfitMargin);
    }
}
