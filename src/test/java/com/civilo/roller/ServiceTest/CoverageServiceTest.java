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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


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
        when(coverageRepository.save(coverage)).thenReturn(coverage);
        final CoverageEntity currentResponse = coverageService.saveCoverage(coverage);
        assertEquals(coverage,currentResponse);
    }

    @Test
    void getCoverages(){
        CoverageEntity coverage = new CoverageEntity(Long.valueOf("9999"), "Arica");
        List<CoverageEntity> expectedAnswer = new ArrayList<>();
        expectedAnswer.add(coverage);
        when((List<CoverageEntity>) coverageRepository.findAll()).thenReturn(expectedAnswer);
        final List<CoverageEntity> currentResponse = coverageService.getCoverages();
        assertEquals(expectedAnswer, currentResponse);
    }

    @Test
    void testGetCoverageById() {
        Long id = 1L;
        CoverageEntity coverageEntity = new CoverageEntity(id, "Santiago");
        when(coverageRepository.findById(id)).thenReturn(Optional.of(coverageEntity));
        Optional<CoverageEntity> result = coverageService.getCoverage(id);
        assertEquals(coverageEntity, result.get());
    }

    @Test
    void testGetCoverageIdByCommune() {
        Long expectedCoverageId = Long.valueOf("9999");
        when(coverageRepository.findIdByCommune("CommuneName")).thenReturn(expectedCoverageId);
        Long actualCoverageId = coverageService.getCoverageIdByCommune("CommuneName");
        assertEquals(expectedCoverageId, actualCoverageId);
    }
}
