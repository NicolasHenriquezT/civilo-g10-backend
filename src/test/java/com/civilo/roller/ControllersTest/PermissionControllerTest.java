package com.civilo.roller.ControllersTest;

import com.civilo.roller.Entities.PermissionEntity;
import com.civilo.roller.controllers.PermissionController;
import com.civilo.roller.services.PermissionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PermissionControllerTest {
    @Mock
    private PermissionService permissionService;
    @InjectMocks
    private PermissionController permissionController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPermissions() {
        List<PermissionEntity> expectedPermissions = new ArrayList<>();
        expectedPermissions.add(new PermissionEntity(1L, "Permission 1", null));
        expectedPermissions.add(new PermissionEntity(2L, "Permission 2", null));
        Mockito.when(permissionService.getPermissions()).thenReturn(expectedPermissions);
        List<PermissionEntity> actualPermissions = permissionController.getPermissions();
        assertEquals(expectedPermissions, actualPermissions);
    }

    @Test
    void testSavePermission() {
        PermissionEntity expectedPermission = new PermissionEntity(1L, "Permission 1", null);
        Mockito.when(permissionService.savePermission(Mockito.any(PermissionEntity.class))).thenReturn(expectedPermission);
        PermissionEntity actualPermission = permissionController.savePermission(new PermissionEntity());
        assertEquals(expectedPermission, actualPermission);
    }
}
