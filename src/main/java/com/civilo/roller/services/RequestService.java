package com.civilo.roller.services;

import com.civilo.roller.Entities.RequestEntity;
import com.civilo.roller.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.civilo.roller.exceptions.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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

    //---------J

    // Get by id
    // Permite obtener la informacion de una solicitud en especifico.
    public Optional<RequestEntity> getRequestById(Long id){
        return requestRepository.findById(id);
    }

    // Create
    // Permite guardar un objeto del tipo "RequestEntity" en la base de datos.
    public RequestEntity createRequest(RequestEntity request){   
        return requestRepository.save(request);  
    }

    // Update
    // Permite actualizar los datos de un objeto del tipo "RequestEntity" en la base de datos.
    public RequestEntity updateRequest(Long requestID, RequestEntity request){

        RequestEntity existingRequest = requestRepository.findById(requestID)
            .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada con el ID: " + requestID));


        existingRequest.setDescription(request.getDescription());
        existingRequest.setDeadline(request.getDeadline());
        existingRequest.setAdmissionDate(request.getAdmissionDate());
        existingRequest.setClosingDate(request.getClosingDate());
        existingRequest.setReason(request.getReason());
    
        RequestEntity updatedRequest = requestRepository.save(existingRequest);
        return updatedRequest;
    }

    // Delete all
    // Permite eliminar todas las solicitudes de un sistema.
    public void deleteRequest() {
        requestRepository.deleteAll();
    }

    // Delete by id
    // Permite eliminar una solicitud en especifico del sistema.
    public void deleteRequestById(Long id){
        requestRepository.deleteById(id);
    }
    
    // Permite verificar si existe una solicitud en el sistema, según el id ingresado.
    public boolean existsRequestById(Long id){
        return requestRepository.findById(id).isPresent();
    }

}
