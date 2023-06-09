package com.civilo.roller.ServiceTest;

import com.civilo.roller.Entities.PipeEntity;
import com.civilo.roller.repositories.PipeRepository;
import com.civilo.roller.services.PipeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class PipeServiceTest {
    @Mock
    private PipeRepository pipeRepository;

    @InjectMocks
    PipeService pipeService;

    @Test
    void getPipes(){
        PipeEntity pipe = new PipeEntity(1L, "Pipe 1");
        List<PipeEntity> expectedAnswer = new ArrayList<>();
        expectedAnswer.add(pipe);
        when((List<PipeEntity>) pipeRepository.findAll()).thenReturn(expectedAnswer);
        final List<PipeEntity> currentResponse = pipeService.getPipes();
        assertEquals(expectedAnswer, currentResponse);
    }

    @Test
    void testGetPipeById() {
        Long id = 1L;
        PipeEntity expectedPipe = new PipeEntity(id, "Pipe 1");
        when(pipeRepository.findById(id)).thenReturn(Optional.of(expectedPipe));
        Optional<PipeEntity> actualPipe = pipeService.getPipe(id);
        assertEquals(expectedPipe, actualPipe.orElse(null));
    }
}
