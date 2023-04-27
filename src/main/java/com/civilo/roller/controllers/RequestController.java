package com.civilo.roller.controllers;

import com.civilo.roller.Entities.RequestEntity;
import com.civilo.roller.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/requests")
public class RequestController {
    @Autowired
    RequestService requestService;

    @GetMapping()
    public List<RequestEntity> getRequests(){
        return requestService.getRequests();
    }

    @PostMapping()
    public RequestEntity saveRequest(@RequestBody RequestEntity request){
        return this.requestService.saveRequest(request);
    }
}
