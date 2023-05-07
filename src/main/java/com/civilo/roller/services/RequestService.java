package com.civilo.roller.services;

import com.civilo.roller.Entities.RequestEntity;
import com.civilo.roller.Entities.SellerEntity;
import com.civilo.roller.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.civilo.roller.exceptions.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


@Service
public class RequestService {
    @Autowired
    RequestRepository requestRepository;

    @Autowired
    SellerService sellerService;

    @Autowired
    StatusService statusService;

    // Get all
    // El siguiente método retorna un listado el cual contiene TODA la información asociada a las solicitudes
    public List<RequestEntity> getRequests(){
        return (List<RequestEntity>) requestRepository.findAll();
    }

    //Get By CLient ID
    //Permite obtener las solicitudes de un cliente especifico
    public ArrayList<RequestEntity> getRequestByUserId(Long id_cliente){
        //List<RequestEntity> listado =  (List<RequestEntity>) requestRepository.findRequestByUserId(id_cliente);
        //System.out.println("HOLAAAAAAAAAAAAAAAAAA"+listado);
        //System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");

        return requestRepository.findRequestByUserId(id_cliente);
    }

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


    //----------------------------------------------------------------------------------------------

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

    public void automaticAssignment(){
        List<RequestEntity> requestEntities = getRequests();
        List<SellerEntity> sellerEntities = sellerService.getSellers();
        Random rand = new Random();
        RequestEntity currentRequest = new RequestEntity();
        for (int i = 0; i < requestEntities.size(); i++){
            currentRequest = requestEntities.get(i);
            if (currentRequest.getSellerId() == 0){
                currentRequest.setSellerId(sellerEntities.get(rand.nextInt(sellerEntities.size())).getUserID().intValue());
                currentRequest.setStatus(statusService.getStatus().get(1));
                requestRepository.save(currentRequest);
            }
        }
    }

}
