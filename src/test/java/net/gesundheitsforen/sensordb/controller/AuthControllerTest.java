package net.gesundheitsforen.sensordb.controller;

import net.gesundheitsforen.sensordb.payload.LoginRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestBody;
import sun.rmi.runtime.Log;

import javax.validation.Valid;

import static org.junit.jupiter.api.Assertions.*;

class AuthControllerTest {

    private AuthController authControllerToTest;

    @Mock
    AuthenticationManager authenticationManager;


    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    //wie die zurückgegebene Response mit Token überprüfen, wenn User in den Repositories der Klasse liegen??
    @Test
    public void authenticateUser() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsernameOrEmail("Testmail@mail.mail");

        authControllerToTest.authenticateUser(loginRequest);

    }

    @Test
    public void registerUser() {
    }
}