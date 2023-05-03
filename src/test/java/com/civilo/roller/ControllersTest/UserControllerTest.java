package com.civilo.roller.ControllersTest;

import com.civilo.roller.Entities.DataTransferObjectEntity;
import com.civilo.roller.Entities.PermissionEntity;
import com.civilo.roller.Entities.RoleEntity;
import com.civilo.roller.Entities.UserEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.civilo.roller.controllers.UserController;
import com.civilo.roller.repositories.UserRepository;
import com.civilo.roller.services.PermissionService;
import com.civilo.roller.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PermissionService permissionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUsers() {
        List<UserEntity> expectedUsers = new ArrayList<>();
        expectedUsers.add(new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20,  new RoleEntity(1L, "Cliente")));
        expectedUsers.add(new UserEntity(Long.valueOf("1111"), "Name", "Surname", "Email", "Password", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20,  new RoleEntity(1L, "Cliente")));
        when(userService.getUsers()).thenReturn(expectedUsers);
        List<UserEntity> actualUsers = userController.getUsers();
        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    void testSaveUser() {
        UserEntity expectedUser = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20,  new RoleEntity(1L, "Cliente"));;
        when(userService.saveUser(Mockito.any(UserEntity.class))).thenReturn(expectedUser);
        UserEntity actualUser = userController.saveUser(new UserEntity());
        assertEquals(expectedUser, actualUser);
    }

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    private MockMvc mockMvc;

    @Test
    public void testLogout() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getSession(false)).thenReturn(session);
        ResponseEntity<?> responseEntity = userController.logout(request);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testLogoutWhenNoSession(){
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getSession(false)).thenReturn(null);
        ResponseEntity<?> responseEntity = userController.logout(request);
        assertNotNull(responseEntity);
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    }
    /*
    @Test
    public void testGetSessionSuccess() {
        HttpSession session = Mockito.mock(HttpSession.class);
        UserEntity user = new UserEntity();
        user.setEmail("test@example.com");
        user.setName("John");
        user.setSurname("Doe");
        RoleEntity role = new RoleEntity();
        role.setAccountType("Test role");
        user.setRole(role);
        PermissionEntity expectedPermission = new PermissionEntity(1L, "Permission 1", role);
        Mockito.when(session.getId()).thenReturn("testSessionId");
        Mockito.when(session.getAttribute("user")).thenReturn(user);
        String result = userController.getSession(session);
        assertEquals("Session ID: testSessionId\n" +
                "User email      : test@example.com\n" +
                "User full name  : John Doe\n" +
                "User role       : Test role\n" +
                "User role ID    : null\n" +
                "User permissions: null\n", result);
    }

    @Test
    public void testLoginSuccess() {
        DataTransferObjectEntity userDTO = new DataTransferObjectEntity();
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password");
        HttpSession session = Mockito.mock(HttpSession.class);
        UserEntity user = new UserEntity();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setName("John");
        user.setSurname("Doe");
        RoleEntity role = new RoleEntity();
        role.setAccountType("Cliente");
        user.setRole(role);
        PermissionEntity expectedPermission = new PermissionEntity(1L, "Permission 1", role);
        Mockito.when(session.getId()).thenReturn("testSessionId");
        Mockito.when(session.getAttribute("user")).thenReturn(user);
        Mockito.when(userService.validateUser(userDTO.getEmail(), userDTO.getPassword())).thenReturn(user);
        Mockito.when(request.getSession()).thenReturn(session);
        ResponseEntity<?> response = userController.login(userDTO, request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Mockito.verify(session).setAttribute("user", user);
    }

    @Test
    public void testLoginUnauthorized() {
        DataTransferObjectEntity userDTO = new DataTransferObjectEntity();
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password");
        ResponseEntity<?> response = userController.login(userDTO, request);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

     */







}
