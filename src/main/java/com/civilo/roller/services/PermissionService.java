package com.civilo.roller.services;

import com.civilo.roller.Entities.PermissionEntity;
import com.civilo.roller.repositories.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {
    @Autowired
    PermissionRepository permissionRepository;

    // El siguiente método retorna un listado el cual contiene TODA la información asociada a los permisos
    public List<PermissionEntity> getPermissions(){
        return (List<PermissionEntity>) permissionRepository.findAll();
    }

    // El siguiente método permite guardar un objeto del tipo "PermissionEntity" en la base de datos
    public PermissionEntity savePermission(PermissionEntity permission){
        return permissionRepository.save(permission);
    }

}
