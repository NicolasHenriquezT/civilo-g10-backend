package com.civilo.roller.controllers;

import com.civilo.roller.Entities.PermissionEntity;
import com.civilo.roller.services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
public class PermissionController {
    @Autowired
    PermissionService permissionService;

    @GetMapping()
    public List<PermissionEntity> getPermissions(){
        return permissionService.getPermissions();
    }

    @PostMapping()
    public PermissionEntity savePermission(@RequestBody PermissionEntity permission){
        return this.permissionService.savePermission(permission);
    }
}
