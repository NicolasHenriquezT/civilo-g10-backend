package com.civilo.roller.ServiceTest;

import com.civilo.roller.Entities.RoleEntity;
import com.civilo.roller.Entities.UserEntity;
import com.civilo.roller.repositories.UserRepository;
import com.civilo.roller.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void saveUser(){
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20,  new RoleEntity(1L, "Cliente"));
        Mockito.when(userRepository.save(user)).thenReturn(user);
        final UserEntity currentResponse = userService.createUser(user);
        assertEquals(user,currentResponse);
    }

    @Test
    void getUsers(){
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20,  new RoleEntity(1L, "Cliente"));
        List<UserEntity> expectedAnswer = new ArrayList<>();
        expectedAnswer.add(user);
        Mockito.when((List<UserEntity>) userRepository.findAll()).thenReturn(expectedAnswer);
        final List<UserEntity> currentResponse = userService.getUsers();
        assertEquals(expectedAnswer, currentResponse);
    }

    @Test
    void getUserById(){
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20,  new RoleEntity(1L, "Cliente"));
        List<UserEntity> expectedAnswer = new ArrayList<>();
        expectedAnswer.add(user);
        Mockito.when(userRepository.findById(Long.valueOf("9999"))).thenReturn(Optional.of(user));
        Optional<UserEntity> currentResponse = userService.getUserById(Long.valueOf("9999"));
        assertEquals(expectedAnswer.get(0).getUserID(), currentResponse.get().getUserID());
    }

    @Test
    void validateUser(){
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20,  new RoleEntity(1L, "Cliente"));
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        user.setPassword("p");
        UserEntity currentResponse = userService.validateUser(user.getEmail(), user.getPassword());
        assertEquals(user, currentResponse);
    }

    @Test
    void validateUser2(){
        UserEntity user = null;
        Mockito.when(userRepository.findByEmail("e")).thenReturn(user);
        UserEntity currentResponse = userService.validateUser("e", "p");
        assertEquals(null, currentResponse);
    }
}
