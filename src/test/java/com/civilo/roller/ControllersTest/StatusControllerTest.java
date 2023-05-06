package com.civilo.roller.ControllersTest;

import com.civilo.roller.Entities.StatusEntity;
import com.civilo.roller.controllers.StatusController;
import com.civilo.roller.services.StatusService;
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
public class StatusControllerTest {
    @Mock
    private StatusService statusService;
    @InjectMocks
    private StatusController statusController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetStatus() {
        List<StatusEntity> expectedStatus = new ArrayList<>();
        expectedStatus.add(new StatusEntity(Long.valueOf("9999"), "Status"));
        expectedStatus.add(new StatusEntity(Long.valueOf("9999"), "Status"));
        Mockito.when(statusService.getStatus()).thenReturn(expectedStatus);
        List<StatusEntity> actualStatus = statusController.getStatus();
        assertEquals(expectedStatus, actualStatus);
    }
    /*
    @Test
    void testSaveStatus() {
        StatusEntity expectedStatus = new StatusEntity(Long.valueOf("9999"), "Status");
        Mockito.when(statusService.saveStatus(Mockito.any(StatusEntity.class))).thenReturn(expectedStatus);
        StatusEntity actualStatus = statusController.saveStatus(new StatusEntity());
        assertEquals(expectedStatus, actualStatus);
    }

     */
}
