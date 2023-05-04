package com.civilo.roller.ControllersTest;

import com.civilo.roller.Entities.RequestEntity;
import com.civilo.roller.Entities.RoleEntity;
import com.civilo.roller.Entities.StatusEntity;
import com.civilo.roller.Entities.UserEntity;
import com.civilo.roller.controllers.RequestController;
import com.civilo.roller.services.RequestService;
import com.civilo.roller.services.StatusService;
import com.civilo.roller.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RequestControllerTest {
    @Mock
    private RequestService requestService;

    @Mock
    private UserService userService;

    @Mock
    private StatusService statusService;
    @InjectMocks
    private RequestController requestController;

    @Mock
    private HttpSession session;

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createRequest_whenUserNotFound_returnsUnauthorized() {
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, role);
        RequestEntity request = new RequestEntity(Long.valueOf("9999"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", 1, null, user, null, null, null);
        when(userService.validateUser(request.getUser().getEmail(), request.getUser().getPassword())).thenReturn(null);
        ResponseEntity<?> response = requestController.createRequest(request);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        verify(requestService, never()).saveRequest(any(RequestEntity.class));
    }

    @Test
    public void createRequest_whenUserIsNotAClient_returnsUnauthorized() {
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "NoCliente");
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, role);
        RequestEntity request = new RequestEntity(Long.valueOf("9999"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", 1, null, user, null, null, null);
        when(userService.validateUser(request.getUser().getEmail(), request.getUser().getPassword())).thenReturn(user);
        ResponseEntity<?> response = requestController.createRequest(request);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        verify(requestService, never()).saveRequest(any(RequestEntity.class));
    }

    @Test
    public void testCreateRequest() {
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, role);
        RequestEntity requestEntity = new RequestEntity(Long.valueOf("9999"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", 1, null, user, null, null, null);
        when(userService.validateUser(user.getEmail(), user.getPassword())).thenReturn(user);
        when(requestService.saveRequest(requestEntity)).thenReturn(requestEntity);
        List<StatusEntity> statusList = new ArrayList<>();
        statusList.add(new StatusEntity(Long.valueOf("1"), "Estado"));
        when(statusService.getStatus()).thenReturn(statusList);
        ResponseEntity<?> response = requestController.createRequest(requestEntity);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetRequests() {
        List<RequestEntity> expectedRequests = new ArrayList<>();
        expectedRequests.add(new RequestEntity(Long.valueOf("1"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", 1, null, null, null, null, null));
        expectedRequests.add(new RequestEntity(Long.valueOf("2"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", 2, null, null, null, null, null));
        when(requestService.getRequests()).thenReturn(expectedRequests);
        List<RequestEntity> actualRequests = requestController.getRequests();
        assertEquals(expectedRequests, actualRequests);
    }

    @Test
    void testSaveRequest() {
        RequestEntity expectedRequest = new RequestEntity(Long.valueOf("2"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", 1, null, null, null, null, null);
        when(requestService.saveRequest(Mockito.any(RequestEntity.class))).thenReturn(expectedRequest);
        RequestEntity actualCoverage = requestController.saveRequest(new RequestEntity());
        assertEquals(expectedRequest, actualCoverage);
    }

    @Test
    void testGetRequestsBySellerId() {
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, role);
        RequestEntity requestEntity = new RequestEntity(Long.valueOf("9999"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", 1, null, user, null, null, null);
        List<RequestEntity> expectedRequests = new ArrayList<>();
        expectedRequests.add(requestEntity);
        Mockito.when(requestService.getRequestBySellerId(Long.valueOf("9999"))).thenReturn(expectedRequests);
        List<RequestEntity> actualRequests = requestController.getRequestsBySellerId(Long.valueOf("9999"));
        assertEquals(expectedRequests, actualRequests);
    }
}