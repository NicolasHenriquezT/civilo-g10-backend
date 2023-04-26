package com.civilo.roller.services;

import com.civilo.roller.Entities.StatusEntity;
import com.civilo.roller.repositories.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {
    @Autowired
    StatusRepository statusRepository;

    // El siguiente método retorna un listado el cual contiene TODA la información asociada a los estados
    public List<StatusEntity> getStatus(){
        return (List<StatusEntity>) statusRepository.findAll();
    }

    // El siguiente método permite guardar un objeto del tipo "StatusEntity" en la base de datos
    public StatusEntity saveStatus(StatusEntity status){
        return statusRepository.save(status);
    }
}
