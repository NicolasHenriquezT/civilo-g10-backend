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
import static org.mockito.Mockito.when;

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
        RequestEntity request = new RequestEntity(Long.valueOf("9999"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason",1, null, null, null, null, null);
        when(requestRepository.save(request)).thenReturn(request);
        final RequestEntity currentResponse = requestService.saveRequest(request);
        assertEquals(request,currentResponse);
    }

    @Test
    void getRequests(){
        RequestEntity request = new RequestEntity(Long.valueOf("9999"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", 1, null, null, null, null, null);
        List<RequestEntity> expectedAnswer = new ArrayList<>();
        expectedAnswer.add(request);
        when((List<RequestEntity>) requestRepository.findAll()).thenReturn(expectedAnswer);
        final List<RequestEntity> currentResponse = requestService.getRequests();
        assertEquals(expectedAnswer, currentResponse);
    }

    @Test
    void getRequestBySellerId() {
        Long sellerId = 123L;
        RequestEntity request1 = new RequestEntity(1L, "Description 1", LocalDate.now(), LocalDate.now(), LocalDate.now(), "Reason 1", 123, null, null, null, null, null);
        RequestEntity request2 = new RequestEntity(2L, "Description 2", LocalDate.now(), LocalDate.now(), LocalDate.now(), "Reason 2", 456, null, null, null, null, null);
        List<RequestEntity> requests = new ArrayList<>();
        requests.add(request1);
        requests.add(request2);
        when(requestService.getRequests()).thenReturn(requests);

        // When
        List<RequestEntity> myRequest = requestService.getRequestBySellerId(sellerId);

        // Then
        assertEquals(1, myRequest.size());
        assertEquals(request1, myRequest.get(0));}

}
