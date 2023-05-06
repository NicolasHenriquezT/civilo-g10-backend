package com.civilo.roller.ServiceTest;

import com.civilo.roller.Entities.CurtainEntity;
import com.civilo.roller.repositories.CurtainRepository;
import com.civilo.roller.services.CurtainService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class CurtainServiceTest {

    @Mock
    private CurtainRepository curtainRepository;

    @InjectMocks
    CurtainService curtainService;

    @Test
    void saveCurtain(){
        CurtainEntity curtain = new CurtainEntity(Long.valueOf("9999"), "Roller");
        when(curtainRepository.save(curtain)).thenReturn(curtain);
        final CurtainEntity currentResponse = curtainService.saveCurtain(curtain);
        assertEquals(curtain,currentResponse);
    }

    @Test
    void getCurtains(){
        CurtainEntity curtain = new CurtainEntity(Long.valueOf("9999"), "Roller");
        List<CurtainEntity> expectedAnswer = new ArrayList<>();
        expectedAnswer.add(curtain);
        when((List<CurtainEntity>) curtainRepository.findAll()).thenReturn(expectedAnswer);
        final List<CurtainEntity> currentResponse = curtainService.getCurtains();
        assertEquals(expectedAnswer, currentResponse);
    }

    @Test
    public void testGetCurtainIdByCurtainType() {
        CurtainEntity curtain = new CurtainEntity(Long.valueOf("9999"), "Curtain 1");
        when(curtainRepository.findIdByCurtainType("Curtain 1")).thenReturn(curtain.getCurtainID());
        Long result = curtainService.getCurtainIdByCurtainType("Curtain 1");
        assertEquals(curtain.getCurtainID(), result);
    }
}
