package nl.aim.regterschotracing.datasource.dao;

import nl.aim.regterschotracing.datasource.databaseutil.DatabaseConnection;
import nl.aim.regterschotracing.domain.exceptions.DatabaseException;

import nl.aim.regterschotracing.domain.services.GraphService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class TabDAOTest {
    //Mock the class you want to test
    @InjectMocks
    TabDAO sut;

    @Mock
    DatabaseConnection mockDatabaseConnection ;
    GraphService mockGraphService;
    ResultSet mockedResultSet;

    UserDAO userDAO;


    @BeforeEach
    void setup() {
        sut = new TabDAO();
        mockDatabaseConnection = mock(DatabaseConnection.class);
        mockedResultSet = mock(ResultSet.class);
        mockGraphService = mock(GraphService.class);
        userDAO = mock(UserDAO.class);
        sut.setDatabaseConnection(mockDatabaseConnection);
        sut.setGraphService(mockGraphService);
        sut.setUserDAO(userDAO);
    }

    @Test
    void getUserTabsCorrectly() throws SQLException {

        when(mockDatabaseConnection.selectQuery(anyString(), any())).thenReturn(mockedResultSet);

        when(mockedResultSet.next()).thenReturn(true).thenReturn(false);

        assertNotNull(sut.getUserTabs("Erik"));
        verify(mockDatabaseConnection).selectQuery(anyString(), any());
    }

    @Test
    void getUserTabsSqlException() throws SQLException{
        when(mockDatabaseConnection.selectQuery(anyString(), any())).thenThrow(SQLException.class);
        assertThrows(DatabaseException.class, () -> {
            sut.getUserTabs("Erik");
        });
        verify(mockDatabaseConnection).selectQuery(anyString(), any());
    }

    @Test
    void deleteTabTest() throws DatabaseException, SQLException {
        doThrow(new SQLException()).when(mockDatabaseConnection).executeUpdate(anyString(), any());
        assertThrows(DatabaseException.class, () -> {
            sut.deleteTab(1,"Erik");
        });
        verify(mockDatabaseConnection).executeUpdate(anyString(), any());
    }

    @Test
    void createTabTest() throws SQLException {
        when(mockDatabaseConnection.selectQuery(anyString(), any())).thenReturn(mockedResultSet);
        when(mockedResultSet.next()).thenReturn(true).thenReturn(false);

        sut = mock(TabDAO.class);

        sut.createTab("Erik", "testTab", 1);
        verify(sut).createTab("Erik", "testTab", 1);
    }

    @Test
    void createTabTestSqlException() throws SQLException {
        when(mockDatabaseConnection.selectQuery(anyString(), any())).thenReturn(mockedResultSet);
        doThrow(SQLException.class).when(mockDatabaseConnection).executeUpdate(anyString(), any());
        when(mockedResultSet.next()).thenReturn(true).thenReturn(false);
        assertThrows(DatabaseException.class, () -> {
            sut.createTab("Erik", "testTab", 1);
        });
        verify(mockDatabaseConnection).executeUpdate(anyString(), any());
    }
}