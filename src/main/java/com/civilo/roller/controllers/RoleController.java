package com.civilo.roller.controllers;

import com.civilo.roller.Entities.RoleEntity;
import com.civilo.roller.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    RoleService roleService;

    @GetMapping()
    public List<RoleEntity> getRoles(){
        return roleService.getRoles();
    }

    @PostMapping()
    public RoleEntity saveRole(@RequestBody RoleEntity role){
        return this.roleService.saveRole(role);
    }
}
