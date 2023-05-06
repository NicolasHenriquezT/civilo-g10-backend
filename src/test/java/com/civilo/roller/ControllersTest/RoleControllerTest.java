package com.civilo.roller.ControllersTest;

import com.civilo.roller.Entities.RoleEntity;
import com.civilo.roller.controllers.RoleController;
import com.civilo.roller.services.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class RoleControllerTest {
    @Mock
    private RoleService roleService;
    @InjectMocks
    private RoleController roleController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRoles() {
        List<RoleEntity> expectedRoles = new ArrayList<>();
        expectedRoles.add(new RoleEntity(1L, "Type 1"));
        expectedRoles.add(new RoleEntity(2L, "Type 2"));
        Mockito.when(roleService.getRoles()).thenReturn(expectedRoles);
        List<RoleEntity> actualRoles = roleController.getRoles();
        assertEquals(expectedRoles, actualRoles);
    }
    /*
    @Test
    void testSaveRole() {
        RoleEntity expectedRole = new RoleEntity(1L, "Type 1");
        Mockito.when(roleService.saveRole(Mockito.any(RoleEntity.class))).thenReturn(expectedRole);
        RoleEntity actualEntity = roleController.saveRole(new RoleEntity());
        assertEquals(expectedRole, actualEntity);
    }
    */

}
