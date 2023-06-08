package com.civilo.roller.services;

import com.civilo.roller.Entities.PipeEntity;
import com.civilo.roller.repositories.PipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PipeService {
    @Autowired
    PipeRepository pipeRepository;

    // Get all
    // El siguiente método retorna un listado el cual contiene TODA la información asociada a los tubos
    public List<PipeEntity> getPipes(){
        return (List<PipeEntity>) pipeRepository.findAll();
    }

    // Get by id
    // Permite obtener la informacion de un pipe en especifico.
    public Optional<PipeEntity> getPipe(Long id){
        return pipeRepository.findById(id);
    }

}
