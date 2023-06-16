package com.civilo.roller.services;

import com.civilo.roller.Entities.ProfitMarginEntity;
import com.civilo.roller.repositories.ProfitMarginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ProfitMarginService {
    @Autowired
    ProfitMarginRepository profitMarginRepository;

    public ProfitMarginEntity getLastProfitMargin(){
        List<ProfitMarginEntity> profitMarginEntities = (List<ProfitMarginEntity>) profitMarginRepository.findAll();
        int size = profitMarginEntities.size();
        return profitMarginEntities.get(size - 1);
    }

    public List<ProfitMarginEntity> getProfitMargins(){
        return (List<ProfitMarginEntity>) profitMarginRepository.findAll();
    }
}
