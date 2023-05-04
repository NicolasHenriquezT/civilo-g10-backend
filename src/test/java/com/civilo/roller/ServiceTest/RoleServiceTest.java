package com.civilo.roller.ServiceTest;

import com.civilo.roller.Entities.RoleEntity;
import com.civilo.roller.repositories.RoleRepository;
import com.civilo.roller.services.RoleService;
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
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    RoleService roleService;

    @Test
    void saveRole(){
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Type 1");
        when(roleRepository.save(role)).thenReturn(role);
        final RoleEntity currentResponse = roleService.saveRole(role);
        assertEquals(role,currentResponse);
    }

    @Test
    void getRoles(){
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Type 1");
        List<RoleEntity> expectedAnswer = new ArrayList<>();
        expectedAnswer.add(role);
        when((List<RoleEntity>) roleRepository.findAll()).thenReturn(expectedAnswer);
        final List<RoleEntity> currentResponse = roleService.getRoles();
        assertEquals(expectedAnswer, currentResponse);
    }

    @Test
    void getRoleIdByAccountType() {
        String accountType = "some account type";
        Long roleId = 123L;
        when(roleRepository.findIdByAccountType(accountType)).thenReturn(roleId);
        assertEquals(roleId, roleService.getRoleIdByAccountType(accountType));
    }
}
