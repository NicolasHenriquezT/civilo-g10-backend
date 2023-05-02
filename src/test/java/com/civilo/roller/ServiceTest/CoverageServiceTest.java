package com.civilo.roller.ServiceTest;

import com.civilo.roller.Entities.CoverageEntity;
import com.civilo.roller.repositories.CoverageRepository;
import com.civilo.roller.services.CoverageService;
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

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class CoverageServiceTest {
    @Mock
    private CoverageRepository coverageRepository;

    @InjectMocks
    CoverageService coverageService;

    @Test
    void saveCoverage(){
        CoverageEntity coverage = new CoverageEntity(Long.valueOf("9999"), "Arica");
        Mockito.when(coverageRepository.save(coverage)).thenReturn(coverage);
        final CoverageEntity currentResponse = coverageService.saveCoverage(coverage);
        assertEquals(coverage,currentResponse);
    }

    @Test
    void getCoverages(){
        CoverageEntity coverage = new CoverageEntity(Long.valueOf("9999"), "Arica");
        List<CoverageEntity> expectedAnswer = new ArrayList<>();
        expectedAnswer.add(coverage);
        Mockito.when((List<CoverageEntity>) coverageRepository.findAll()).thenReturn(expectedAnswer);
        final List<CoverageEntity> currentResponse = coverageService.getCoverages();
        assertEquals(expectedAnswer, currentResponse);
    }
}