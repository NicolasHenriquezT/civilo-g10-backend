package com.civilo.roller.services;

import com.civilo.roller.Entities.CoverageEntity;
import com.civilo.roller.repositories.CoverageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoverageService {
    @Autowired
    CoverageRepository coverageRepository;

    // El siguiente método retorna un listado el cual contiene TODA la información asociada a la cobertura
    public List<CoverageEntity> getCoverages(){
        return (List<CoverageEntity>) coverageRepository.findAll();
    }

    // El siguiente método permite guardar un objeto del tipo "CoverageEntity" en la base de datos
    public CoverageEntity saveCoverage(CoverageEntity coverage){
        return coverageRepository.save(coverage);
    }
}
