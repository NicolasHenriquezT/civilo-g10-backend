package com.civilo.roller.ControllersTest;

import com.civilo.roller.Entities.PipeEntity;
import com.civilo.roller.controllers.PipeController;
import com.civilo.roller.services.PipeService;
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
public class PipeControllerTest {
    @Mock
    private PipeService pipeService;
    @InjectMocks
    private PipeController pipeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPipes() {
        List<PipeEntity> expectedPipes = new ArrayList<>();
        expectedPipes.add(new PipeEntity(1L, "Pipe 1"));
        expectedPipes.add(new PipeEntity(2L, "Pipe 2"));
        Mockito.when(pipeService.getPipes()).thenReturn(expectedPipes);
        List<PipeEntity> actualPipe = pipeController.getPipes();
        assertEquals(expectedPipes, actualPipe);
    }
}
