package com.civilo.roller.services;

import com.civilo.roller.Entities.RoleEntity;
import com.civilo.roller.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    // El siguiente método retorna un listado el cual contiene TODA la información asociada a los roles
    public List<RoleEntity> getRoles(){
        return (List<RoleEntity>) roleRepository.findAll();
    }

    // El siguiente método permite guardar un objeto del tipo "RoleEntity" en la base de datos
    public RoleEntity saveRole(RoleEntity role){
        return roleRepository.save(role);
    }

    //Se obtiene la ID del rol dependiendo del tipo de cuenta
    public Long getRoleIdByAccountType(String accountType){
        return roleRepository.findIdByAccountType(accountType);

    }
}
