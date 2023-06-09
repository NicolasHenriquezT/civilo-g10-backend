package com.civilo.roller.ControllersTest;

import com.civilo.roller.Entities.IVAEntity;
import com.civilo.roller.controllers.IVAController;
import com.civilo.roller.services.IVAService;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class IVAControllerTest {
    @Mock
    private IVAService ivaService;
    @InjectMocks
    private IVAController ivaController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getIVA() {
        List<IVAEntity> expectedIVA = new ArrayList<>();
        expectedIVA.add(new IVAEntity(1L, 19f));
        Mockito.when(ivaService.getIVAPercentage()).thenReturn(expectedIVA.get(0).getIvaPercentage());
        float actualIVA = ivaController.getIVAPercentage();
        assertEquals(expectedIVA.get(0).getIvaPercentage(), actualIVA);
    }
}
