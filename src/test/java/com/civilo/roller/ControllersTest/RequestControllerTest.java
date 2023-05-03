package com.civilo.roller.ControllersTest;

import com.civilo.roller.Entities.RequestEntity;
import com.civilo.roller.Entities.RoleEntity;
import com.civilo.roller.Entities.UserEntity;
import com.civilo.roller.controllers.RequestController;
import com.civilo.roller.services.RequestService;
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
    @InjectMocks
    private RequestController requestController;

    @Mock
    private HttpSession session;

    @Mock
    private HttpServletRequest request;
    /*
    @Test
    void testCreateRequest() {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession(false)).thenReturn(session);
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, role);
        when(session.getAttribute("user")).thenReturn(user);
        RequestEntity requestEntity = new RequestEntity(Long.valueOf("9999"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", null, null, null, null, null);
        ResponseEntity<?> response = requestController.createRequest(requestEntity, request);
        verify(requestService).saveRequest(requestEntity);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testCreateRequestUnauthorizedUser() {
        UserEntity user = new UserEntity(Long.valueOf("1"), "Nombre", "Apellido", "email@test.com", "contraseña", "123456789", "Comuna", LocalDate.now(), 20, new RoleEntity(1L, "Cliente"));
        user.setRole(new RoleEntity(Long.valueOf("9999"), "Vendedor"));
        RequestEntity request = new RequestEntity();
        request.setDescription("Necesito vender mi producto");
        request.setAdmissionDate(LocalDate.now());
        request.setUser(user);
        HttpSession session = Mockito.mock(HttpSession.class);
        Mockito.when(session.getAttribute("user")).thenReturn(user);
        HttpServletRequest requestMock = Mockito.mock(HttpServletRequest.class);
        Mockito.when(requestMock.getSession(false)).thenReturn(session);
        RequestService requestService = Mockito.mock(RequestService.class);
        ResponseEntity<?> responseEntity = requestController.createRequest(request, requestMock);
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

    @Test
    void testCreateRequestWithoutSession() {
        RequestController controller = new RequestController();
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getSession(false)).thenReturn(null);
        RequestEntity requestEntity = new RequestEntity();
        ResponseEntity<?> response = controller.createRequest(requestEntity, request);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

     */

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRequests() {
        List<RequestEntity> expectedRequests = new ArrayList<>();
        expectedRequests.add(new RequestEntity(Long.valueOf("1"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", null, null, null, null, null));
        expectedRequests.add(new RequestEntity(Long.valueOf("2"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", null, null, null, null, null));
        when(requestService.getRequests()).thenReturn(expectedRequests);
        List<RequestEntity> actualRequests = requestController.getRequests();
        assertEquals(expectedRequests, actualRequests);
    }

    @Test
    void testSaveRequest() {
        RequestEntity expectedRequest = new RequestEntity(Long.valueOf("2"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", null, null, null, null, null);
        when(requestService.saveRequest(Mockito.any(RequestEntity.class))).thenReturn(expectedRequest);
        RequestEntity actualCoverage = requestController.saveRequest(new RequestEntity());
        assertEquals(expectedRequest, actualCoverage);
    }
}
