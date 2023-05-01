package com.civilo.roller.ServiceTest;

import com.civilo.roller.Entities.PermissionEntity;
import com.civilo.roller.Entities.RoleEntity;
import com.civilo.roller.repositories.PermissionRepository;
import com.civilo.roller.services.PermissionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class PermissionServiceTest {
    @Mock
    private PermissionRepository permissionRepository;

    @InjectMocks
    PermissionService permissionService;

    @Test
    void savePermission(){
        PermissionEntity permission = new PermissionEntity(Long.valueOf("9999"), "Permiso 1", null);
        Mockito.when(permissionRepository.save(permission)).thenReturn(permission);
        final PermissionEntity currentResponse = permissionService.savePermission(permission);
        assertEquals(permission,currentResponse);
    }

    @Test
    void getPermissions(){
        PermissionEntity permission = new PermissionEntity(Long.valueOf("9999"), "Permiso 1", null);
        List<PermissionEntity> expectedAnswer = new ArrayList<>();
        expectedAnswer.add(permission);
        Mockito.when((List<PermissionEntity>) permissionRepository.findAll()).thenReturn(expectedAnswer);
        final List<PermissionEntity> currentResponse = permissionService.getPermissions();
        assertEquals(expectedAnswer, currentResponse);
    }

    @Test
    void rolePermissions(){
        RoleEntity role = new RoleEntity(Long.valueOf("1111"), "Tipo de cuenta 1");
        PermissionEntity permission = new PermissionEntity(Long.valueOf("9999"), "Permiso 1", role);
        List<PermissionEntity> expectedAnswer = new ArrayList<>();
        expectedAnswer.add(permission);
        Mockito.when((List<PermissionEntity>) permissionRepository.findAll()).thenReturn(expectedAnswer);
        String currentAnswer = permissionService.rolePermissions(Long.valueOf("1234"));
        assertEquals("", currentAnswer);
    }

    @Test
    void rolePermissions2(){
        RoleEntity role = new RoleEntity(Long.valueOf("1111"), "Tipo de cuenta 1");
        PermissionEntity permission = new PermissionEntity(Long.valueOf("9999"), "Permiso 1", role);
        List<PermissionEntity> expectedAnswer = new ArrayList<>();
        expectedAnswer.add(permission);
        Mockito.when((List<PermissionEntity>) permissionRepository.findAll()).thenReturn(expectedAnswer);
        String currentAnswer = "";
        currentAnswer = currentAnswer + permissionService.rolePermissions(role.getRoleID());
        assertEquals("Permiso 1\n                  ", currentAnswer);
    }
}
