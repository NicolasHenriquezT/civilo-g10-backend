package com.civilo.roller.ServiceTest;

import com.civilo.roller.Entities.StatusEntity;
import com.civilo.roller.repositories.StatusRepository;
import com.civilo.roller.services.StatusService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class StatusServiceTest {
    @Mock
    private StatusRepository statusRepository;

    @InjectMocks
    StatusService statusService;

    @Test
    void saveStatus(){
        StatusEntity status = new StatusEntity(Long.valueOf("9999"), "Status");
        Mockito.when(statusRepository.save(status)).thenReturn(status);
        final StatusEntity currentResponse = statusService.saveStatus(status);
        assertEquals(status,currentResponse);
    }

    @Test
    void getStatus(){
        StatusEntity status = new StatusEntity(Long.valueOf("9999"), "Status");
        List<StatusEntity> expectedAnswer = new ArrayList<>();
        expectedAnswer.add(status);
        Mockito.when((List<StatusEntity>) statusRepository.findAll()).thenReturn(expectedAnswer);
        final List<StatusEntity> currentResponse = statusService.getStatus();
        assertEquals(expectedAnswer, currentResponse);
    }
}
