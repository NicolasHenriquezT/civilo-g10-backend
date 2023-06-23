package com.civilo.roller.ControllersTest;

import com.civilo.roller.Entities.ProfitMarginEntity;
import com.civilo.roller.controllers.ProfitMarginController;
import com.civilo.roller.repositories.ProfitMarginRepository;
import com.civilo.roller.services.ProfitMarginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProfitMarginControllerTest {
    @Mock
    private ProfitMarginRepository profitMarginRepository;

    @InjectMocks
    private ProfitMarginController profitMarginController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    /*
    @Test
    public void testGetProfitMargins() {
        // Arrange
        ProfitMarginEntity margin1 = new ProfitMarginEntity(1L, 0.1f, 0.1f);
        ProfitMarginEntity margin2 = new ProfitMarginEntity(2L, 0.2f, 0.2f);
        List<ProfitMarginEntity> mockProfitMargins = Arrays.asList(margin1, margin2);
        when(profitMarginRepository.findAll()).thenReturn(mockProfitMargins);

        // Act
        List<ProfitMarginEntity> result = profitMarginController.getProfitMargins();

        // Assert
        assertEquals(mockProfitMargins, result);
        verify(profitMarginRepository, times(1)).findAll();
    }

     */
}
