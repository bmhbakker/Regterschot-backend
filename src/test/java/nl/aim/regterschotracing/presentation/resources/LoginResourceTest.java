package nl.aim.regterschotracing.presentation.resources;

import nl.aim.regterschotracing.domain.exceptionmappers.LoginCredentialsIncorrectExceptionMapper;
import nl.aim.regterschotracing.domain.exceptions.LoginCredentialsIncorrectException;
import nl.aim.regterschotracing.domain.services.LoginService;
import nl.aim.regterschotracing.presentation.dto.login.LoginRequestDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class LoginResourceTest {
    @Mock
    private LoginResource sut;


    @InjectMocks
    LoginService loginService;


    @BeforeEach
    public void setup() {
        sut = new LoginResource();
        loginService = mock(LoginService.class);
    }

    @Test
    void getUserHTMLcodeWithCorrectInformation() {

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();

        sut.setLoginService(loginService);
        loginRequestDTO.setUsername("Erik");
        loginRequestDTO.setPassword("Welkom123");

        var http_code = 200;

        var testValue = sut.login(loginRequestDTO);

        assertEquals(http_code, testValue.getStatus());
    }

    //With @Provider the LoginCredentialsIncorrect will be thrown, this test is to show that it would return the 403 status from the mapper.
    @Test
    void getUserHTMLcodeWithIncorrectInformation() throws LoginCredentialsIncorrectException {
        //Arrange
        LoginCredentialsIncorrectException loginException = new LoginCredentialsIncorrectException("User not found");
        LoginCredentialsIncorrectExceptionMapper incorrectInformationException = new LoginCredentialsIncorrectExceptionMapper();
        incorrectInformationException.toResponse(loginException);

        var actualcode = incorrectInformationException.toResponse(loginException).getStatus();
        var http_code = 403;
        //Act & Assert

        assertEquals(http_code, actualcode);
    }

    @Test
    void checkExceptionThrown() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();

        sut.setLoginService(loginService);
        loginRequestDTO.setUsername("Erik");
        loginRequestDTO.setPassword("Welkm123");

        when(loginService.checkLogin(loginRequestDTO)).thenThrow(new LoginCredentialsIncorrectException("User not found"));


        assertThrows(LoginCredentialsIncorrectException.class, () -> {
            sut.login(loginRequestDTO);
        });
    }

}