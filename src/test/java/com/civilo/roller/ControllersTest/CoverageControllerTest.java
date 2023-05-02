package com.civilo.roller.ControllersTest;

import com.civilo.roller.Entities.CoverageEntity;
import com.civilo.roller.controllers.CoverageController;
import com.civilo.roller.services.CoverageService;
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
public class CoverageControllerTest {
    @Mock
    private CoverageService coverageService;
    @InjectMocks
    private CoverageController coverageController;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testGetCoverages() {
        List<CoverageEntity> expectedCoverages = new ArrayList<>();
        expectedCoverages.add(new CoverageEntity(1L, "Coverage 1"));
        expectedCoverages.add(new CoverageEntity(2L, "Coverage 2"));
        Mockito.when(coverageService.getCoverages()).thenReturn(expectedCoverages);
        List<CoverageEntity> actualCoverages = coverageController.getCoverages();
        assertEquals(expectedCoverages, actualCoverages);
    }

    @Test
    void testSaveCoverage() {
        CoverageEntity expectedCoverage = new CoverageEntity(1L, "Coverage 1");
        Mockito.when(coverageService.saveCoverage(Mockito.any(CoverageEntity.class))).thenReturn(expectedCoverage);
        CoverageEntity actualCoverage = coverageController.saveCoverage(new CoverageEntity());
        assertEquals(expectedCoverage, actualCoverage);
    }
}
