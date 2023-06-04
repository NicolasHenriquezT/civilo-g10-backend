package com.civilo.roller.services;

import com.civilo.roller.Entities.IVAEntity;
import com.civilo.roller.repositories.IVARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IVAService {
    @Autowired
    IVARepository ivaRepository;
    // Get all
    // El siguiente método retorna un listado el cual contiene TODA la información asociada al IVA
    public float getIVAPercentage(){
        List<IVAEntity> ivaEntityList = (List<IVAEntity>) ivaRepository.findAll();
        Integer size = ivaEntityList.size();
        float percentage = ivaEntityList.get(size - 1).getIvaPercentage();
        return percentage;
    }


}
