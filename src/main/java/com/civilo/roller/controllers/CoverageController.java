package com.civilo.roller.controllers;

import com.civilo.roller.Entities.CoverageEntity;
import com.civilo.roller.services.CoverageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coverages")
public class CoverageController {
    @Autowired
    CoverageService coverageService;

    @GetMapping()
    public List<CoverageEntity> getCoverages(){
        return coverageService.getCoverages();
    }

    @PostMapping()
    public CoverageEntity saveCoverage(@RequestBody CoverageEntity coverage){
        return this.coverageService.saveCoverage(coverage);
    }
}
