package com.civilo.roller.services;

import com.civilo.roller.Entities.CurtainEntity;
import com.civilo.roller.repositories.CurtainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class CurtainService {
    @Autowired
    CurtainRepository curtainRepository;

    // El siguiente método retorna un listado el cual contiene TODA la información asociada a las cortinas
    public List<CurtainEntity> getCurtains(){
        return (List<CurtainEntity>) curtainRepository.findAll();
    }

    // El siguiente método permite guardar un objeto del tipo "CoverageEntity" en la base de datos
    public CurtainEntity saveCurtain(CurtainEntity curtain){
        return curtainRepository.save(curtain);
    }

}
