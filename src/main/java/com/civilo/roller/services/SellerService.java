package com.civilo.roller.services;

import com.civilo.roller.Entities.SellerEntity;
import com.civilo.roller.Entities.UserEntity;
import com.civilo.roller.repositories.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerService {
    @Autowired
    SellerRepository sellerRepository;

    // El siguiente método retorna un listado el cual contiene TODA la información asociada a los vendedores
    public List<SellerEntity> getSellers(){
        return (List<SellerEntity>) sellerRepository.findAll();
    }

    // El siguiente método permite guardar un objeto del tipo "SellerEntity" en la base de datos
    public SellerEntity saveSeller(SellerEntity seller){
        return sellerRepository.save(seller);
    }

    public SellerEntity validateSeller(String email, String password){
        SellerEntity seller = sellerRepository.findByEmail(email);
        if (seller != null && seller.getPassword().equals(password)){
            return seller;
        }
        return null;
    }

}
