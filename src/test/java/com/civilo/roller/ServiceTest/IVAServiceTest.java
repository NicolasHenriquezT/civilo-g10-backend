package com.civilo.roller.ServiceTest;

import com.civilo.roller.Entities.IVAEntity;
import com.civilo.roller.repositories.IVARepository;
import com.civilo.roller.services.IVAService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class IVAServiceTest {
    @Mock
    private IVARepository ivaRepository;

    @InjectMocks
    private IVAService ivaService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetIVAPercentage() {
        List<IVAEntity> ivaEntityList = new ArrayList<>();
        ivaEntityList.add(new IVAEntity(1L, 19.0f));
        ivaEntityList.add(new IVAEntity(2L, 21.0f));
        when(ivaRepository.findAll()).thenReturn(ivaEntityList);

        float result = ivaService.getIVAPercentage();

        assertEquals(21.0f, result); // Verifica que el resultado sea el porcentaje de IVA esperado
    }

    @Test
    public void testGetIVAPercentageEmptyList() {
        List<IVAEntity> ivaEntityList = new ArrayList<>();
        when(ivaRepository.findAll()).thenReturn(ivaEntityList);

        float result = ivaService.getIVAPercentage();

        assertEquals(0.0f, result); // Verifica que el resultado sea 0 cuando la lista está vacía
    }
}
