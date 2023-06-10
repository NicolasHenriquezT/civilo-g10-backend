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
        float percentage;

        //ESTE IF LO HICE PARA CORREGIR EL PROBLEMA DE QUE SI SIZE ES CERO HAY VIOLACIÓN DE SEGMENTO
        //nose que es lo que hace esta funcion y cual es su objetivo, el arreglo que hice fue momentaneo
        //el que hizo esto que lo vea porfa para que vea que entregue la respuesta que se busca en caso de tener un size de 0
        if(size > 0){
            percentage = ivaEntityList.get(size - 1).getIvaPercentage();
        }
        else{
            percentage = 0;
        }
        return percentage;
    }

    public IVAEntity getLastIVA(){
        List<IVAEntity> ivaEntityList = (List<IVAEntity>) ivaRepository.findAll();
        Integer size = ivaEntityList.size();
        return ivaEntityList.get(size - 1);
    }

    public IVAEntity getIVAByPercentage(float percentage){
        List<IVAEntity> ivaEntities = (List<IVAEntity>) ivaRepository.findAll();
        for (int i = 0; i < ivaEntities.size(); i++){
            if (percentage == ivaEntities.get(i).getIvaPercentage()){
                return ivaEntities.get(i);
            }
        }
        return null;
    }


}
