package nl.aim.regterschotracing.domain.services;


import de.mkammerer.argon2.Argon2;
import nl.aim.regterschotracing.datasource.dao.UserDAO;
import nl.aim.regterschotracing.datasource.databaseutil.DatabaseConnection;

import nl.aim.regterschotracing.domain.exceptions.LoginCredentialsIncorrectException;
import nl.aim.regterschotracing.presentation.dto.login.LoginRequestDTO;
import nl.aim.regterschotracing.presentation.dto.login.LoginResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;


import java.sql.ResultSet;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class LoginServiceTest {

    @Mock
    LoginService sut;
    @Mock
    LoginRequestDTO databaseResponse;
    @InjectMocks
    DatabaseConnection mockConnection;
    @InjectMocks
    LoginResponseDTO mockedLoginResponseDTO;
    @InjectMocks
    UserDAO user;
    LoginRequestDTO mockRequest;
    ResultSet mockedResultSet;

    Argon2 mockArgon;


    @BeforeEach
    public void setup() {
        sut = new LoginService();
        sut.setLoginResponseDTO(mockedLoginResponseDTO = new LoginResponseDTO());
        user = Mockito.mock(UserDAO.class);
        mockConnection = mock(DatabaseConnection.class);
        mockedResultSet = mock(ResultSet.class);
        user.setDatabaseConnection(mockConnection);
        sut.setUserDAO(user);
        databaseResponse = new LoginRequestDTO();
        databaseResponse.setUsername("Erik");
        databaseResponse.setPassword("$argon2id$v=19$m=65536,t=3,p=1$KkuO1XL9YLkT1dXlIMvnkA$26vTABJbbHZAfduxijlX191Pqb7ffG2pNI3UCdaVL6o");
        mockRequest = new LoginRequestDTO();
        mockArgon = mock(Argon2.class);
        when(mockArgon.verify(any(), (char[]) any())).thenReturn(false);
    }

    @Test
    void checkCorrectUsernameResponse(){
        //Arrange
        LoginRequestDTO mockRequest = new LoginRequestDTO();
        LoginResponseDTO expectedResponse = new LoginResponseDTO();
        LoginResponseDTO actualResponse;

        expectedResponse.setToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjo0MiwidXNlcm5hbWUiOiJFcmlrIn0.FA26z63uwWWOfOM8EHfdBiYw6DzcfAF6EJ4YzZiQ-q0");
        expectedResponse.setUser("Erik");

        mockRequest.setUsername("Erik");
        mockRequest.setPassword("Welkom123");


        //Act
        Mockito.when(user.getUser("Erik")).thenReturn(databaseResponse);
        actualResponse = sut.checkLogin(mockRequest);


        //Arrange
        assertEquals(expectedResponse.getUser(), actualResponse.getUser());

    }

    @Test
    void checkCorrectTokenResponse() {
        //Arrange
        LoginRequestDTO mockRequest = new LoginRequestDTO();
        LoginResponseDTO expectedResponse = new LoginResponseDTO();
        LoginResponseDTO actualResponse;

        expectedResponse.setToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjo0MiwidXNlcm5hbWUiOiJFcmlrIn0.FA26z63uwWWOfOM8EHfdBiYw6DzcfAF6EJ4YzZiQ-q0");
        expectedResponse.setUser("Erik");

        mockRequest.setUsername("Erik");
        mockRequest.setPassword("Welkom123");


        //Act
        Mockito.when(user.getUser("Erik")).thenReturn(databaseResponse);
        actualResponse = sut.checkLogin(mockRequest);


        //Arrange
        assertEquals(expectedResponse.getToken(), actualResponse.getToken());
    }

    @Test
    void checkThrownExceptionWrongPassword() {
        //Arrange
        LoginRequestDTO mockRequest = new LoginRequestDTO();

        mockRequest.setUsername("Erik");
        mockRequest.setPassword("WrongPassword");


        //Act
        Mockito.when(user.getUser("Erik")).thenReturn(databaseResponse);


        //Assert
        assertThrows(LoginCredentialsIncorrectException.class, () -> {
            sut.checkLogin(mockRequest);
        });

    }

    @Test
    void checkThrownExceptionWrongUser() {
        //Arrange
        databaseResponse.setUsername(null);
        databaseResponse.setPassword(null);


        mockRequest.setUsername("WrongUser");
        mockRequest.setPassword("Welkom123");


        //Act
        Mockito.when(user.getUser(null)).thenReturn(databaseResponse);


        //Arrange
        assertThrows(LoginCredentialsIncorrectException.class, () -> {
            sut.checkLogin(mockRequest);
        });

    }

    @Test
    void checkThrownExceptionNoUserAndPassword() {
        //Arrange
        databaseResponse.setUsername(null);
        databaseResponse.setPassword(null);
        LoginRequestDTO mockRequest = new LoginRequestDTO();

        mockRequest.setUsername("");
        mockRequest.setPassword("");


        //Act
        Mockito.when(user.getUser(null)).thenReturn(databaseResponse);


        //Arrange
        assertThrows(LoginCredentialsIncorrectException.class, () -> {
            sut.checkLogin(mockRequest);
        });

    }

    @Test
    void testCreationToken() {
        //Arrange
        String actualToken;
        String expectedToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjo0MiwidXNlcm5hbWUiOiJFcmlrIn0.FA26z63uwWWOfOM8EHfdBiYw6DzcfAF6EJ4YzZiQ-q0";
        //Act
        actualToken = sut.createJWTtoken("Erik");
        //Assert
        assertEquals(expectedToken, actualToken);
    }
}