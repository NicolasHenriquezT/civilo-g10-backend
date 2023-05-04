package com.civilo.roller.services;

import com.civilo.roller.Entities.RequestEntity;
import com.civilo.roller.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestService {
    @Autowired
    RequestRepository requestRepository;

    // El siguiente método retorna un listado el cual contiene TODA la información asociada a las solicitudes
    public List<RequestEntity> getRequests(){
        return (List<RequestEntity>) requestRepository.findAll();
    }

    // El siguiente método permite guardar un objeto del tipo "RequestEntity" en la base de datos
    public RequestEntity saveRequest(RequestEntity request){
        return requestRepository.save(request);
    }

    public List<RequestEntity> getRequestBySellerId(Long sellerId) {
        List<RequestEntity> requests = getRequests();
        List<RequestEntity> myRequest = new ArrayList<>();
        for (int i = 0; i < requests.size(); i++){
            if (requests.get(i).getSellerId() == sellerId){
                myRequest.add(requests.get(i));
            }
        }
        return myRequest;
    }
}
