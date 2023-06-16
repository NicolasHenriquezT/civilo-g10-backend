package com.civilo.roller.ServiceTest;

import com.civilo.roller.Entities.ProfitMarginEntity;
import com.civilo.roller.repositories.ProfitMarginRepository;
import com.civilo.roller.services.ProfitMarginService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProfitMarginServiceTest {
    @Mock
    private ProfitMarginRepository profitMarginRepository;

    @InjectMocks
    private ProfitMarginService profitMarginService;

    @Test
    public void testGetLastProfitMargin() {
        // Crear una instancia simulada de ProfitMarginEntity
        ProfitMarginEntity profitMarginEntity = new ProfitMarginEntity(1L, 40f, 0.4f);

        // Configurar el comportamiento del mock repository
        when(profitMarginRepository.findAll()).thenReturn(List.of(profitMarginEntity));

        // Llamar al método getLastProfitMargin()
        ProfitMarginEntity lastProfitMargin = profitMarginService.getLastProfitMargin();

        // Verificar que se devuelva la instancia simulada
        assertEquals(profitMarginEntity, lastProfitMargin);
    }

    @Test
    public void testGetProfitMargins() {
        // Crear una lista simulada de ProfitMarginEntity
        List<ProfitMarginEntity> profitMargins = new ArrayList<>();
        profitMargins.add(new ProfitMarginEntity(1L, 30f, 0.3f));
        profitMargins.add(new ProfitMarginEntity(2L, 40f, 0.4f));
        profitMargins.add(new ProfitMarginEntity(3L, 50f, 0.5f));

        // Configurar el comportamiento del mock repository
        when(profitMarginRepository.findAll()).thenReturn(profitMargins);

        // Llamar al método getProfitMargins()
        List<ProfitMarginEntity> returnedProfitMargins = profitMarginService.getProfitMargins();

        // Verificar que se devuelva la lista simulada
        assertEquals(profitMargins, returnedProfitMargins);
    }
}
