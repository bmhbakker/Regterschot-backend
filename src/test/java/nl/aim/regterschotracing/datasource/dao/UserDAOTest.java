package nl.aim.regterschotracing.datasource.dao;

import nl.aim.regterschotracing.datasource.databaseutil.DatabaseConnection;
import nl.aim.regterschotracing.domain.exceptions.DatabaseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserDAOTest {
    @InjectMocks
    UserDAO sut;

    @Mock
    DatabaseConnection mockDatabaseConnection ;
    ResultSet mockedResultSet;


    @BeforeEach
    void setup() {
        sut = new UserDAO();
        mockDatabaseConnection = mock(DatabaseConnection.class);
        mockedResultSet = mock(ResultSet.class);
        sut.setDatabaseConnection(mockDatabaseConnection);
    }

    @Test
    void getUserCorrectly() throws SQLException {

        when(mockDatabaseConnection.selectQuery(anyString(), any())).thenReturn(mockedResultSet);

        when(mockedResultSet.next()).thenReturn(true).thenReturn(false);

        assertNotNull(sut.getUser("Erik"));
        verify(mockDatabaseConnection).selectQuery(anyString(), any());
    }

    @Test
    void getUserSqlException() throws SQLException {
        when(mockDatabaseConnection.selectQuery(anyString(), any())).thenThrow(SQLException.class);
        assertThrows(DatabaseException.class, () -> {
            sut.getUser("Erik");
        });
        verify(mockDatabaseConnection).selectQuery(anyString(), any());
    }

    @Test
    void getUserIDCorrectly() throws SQLException {
        int expectedResult = 1;
        when(mockDatabaseConnection.selectQuery(anyString(), any())).thenReturn(mockedResultSet);

        when(mockedResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockedResultSet.getInt("UserID")).thenReturn(1);
        int actualResult = sut.getUserID("Erik");
        assertEquals(expectedResult, actualResult);
        verify(mockDatabaseConnection).selectQuery(anyString(), any());
    }
}
