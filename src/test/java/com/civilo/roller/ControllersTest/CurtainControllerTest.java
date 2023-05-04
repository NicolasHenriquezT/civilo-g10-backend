package com.civilo.roller.ControllersTest;

import com.civilo.roller.Entities.CurtainEntity;
import com.civilo.roller.controllers.CurtainController;
import com.civilo.roller.services.CurtainService;
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
public class CurtainControllerTest {
    @Mock
    private CurtainService curtainService;
    @InjectMocks
    private CurtainController curtainController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCurtains() {
        List<CurtainEntity> expectedCurtains = new ArrayList<>();
        expectedCurtains.add(new CurtainEntity(1L, "Curtain 1"));
        expectedCurtains.add(new CurtainEntity(2L, "Curtain 2"));
        Mockito.when(curtainService.getCurtains()).thenReturn(expectedCurtains);
        List<CurtainEntity> actualCurtain = curtainController.getCurtains();
        assertEquals(expectedCurtains, actualCurtain);
    }

    @Test
    void saveCurtain() {
        CurtainEntity expectedCurtain = new CurtainEntity(1L, "Curtain 1");
        Mockito.when(curtainService.saveCurtain(Mockito.any(CurtainEntity.class))).thenReturn(expectedCurtain);
        CurtainEntity actualCurtain = curtainController.saveCurtain(new CurtainEntity());
        assertEquals(expectedCurtain, actualCurtain);
    }
}
