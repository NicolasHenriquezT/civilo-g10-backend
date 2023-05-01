package com.civilo.roller.ServiceTest;

import com.civilo.roller.Entities.RequestEntity;
import com.civilo.roller.repositories.RequestRepository;
import com.civilo.roller.services.RequestService;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class RequestServiceTest {
    @Mock
    private RequestRepository requestRepository;

    @InjectMocks
    RequestService requestService;

    @Test
    void saveRequest(){
        RequestEntity request = new RequestEntity(Long.valueOf("9999"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", null, null, null, null, null);
        Mockito.when(requestRepository.save(request)).thenReturn(request);
        final RequestEntity currentResponse = requestService.saveRequest(request);
        assertEquals(request,currentResponse);
    }

    @Test
    void getRequests(){
        RequestEntity request = new RequestEntity(Long.valueOf("9999"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", null, null, null, null, null);
        List<RequestEntity> expectedAnswer = new ArrayList<>();
        expectedAnswer.add(request);
        Mockito.when((List<RequestEntity>) requestRepository.findAll()).thenReturn(expectedAnswer);
        final List<RequestEntity> currentResponse = requestService.getRequests();
        assertEquals(expectedAnswer, currentResponse);
    }
}
