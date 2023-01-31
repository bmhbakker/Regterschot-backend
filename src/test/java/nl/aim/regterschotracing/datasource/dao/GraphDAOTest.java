package nl.aim.regterschotracing.datasource.dao;

import nl.aim.regterschotracing.datasource.databaseutil.DatabaseConnection;
import nl.aim.regterschotracing.domain.exceptions.DatabaseException;
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

class GraphDAOTest {

    Integer tabID = 1;
    Integer graphID = 1;
    String graphType = "LineChart";

    @InjectMocks
    GraphDAO sut;
    @Mock
    DatabaseConnection mockDatabaseConnection;
    ResultSet mockedResultSet;

    @BeforeEach
    void setup() {
        sut = new GraphDAO();
        mockDatabaseConnection = mock(DatabaseConnection.class);
        mockedResultSet = mock(ResultSet.class);
        sut.setDatabaseConnection(mockDatabaseConnection);
    }

    @Test
    void getGraphs() throws SQLException {
        when(mockDatabaseConnection.selectQuery(anyString(), any())).thenReturn(mockedResultSet);
        when(mockedResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockedResultSet.getString("GraphType")).thenReturn("gauge");

        assertNotNull(sut.getGraphs(tabID));

        verify(mockDatabaseConnection, atLeast(2)).selectQuery(anyString(), any());
    }

    @Test
    void getUserTabsSqlException() throws SQLException {
        when(mockDatabaseConnection.selectQuery(anyString(), any())).thenThrow(SQLException.class);
        assertThrows(DatabaseException.class, () -> {
            sut.getGraphs(tabID);
        });
        verify(mockDatabaseConnection).selectQuery(anyString(), any());
    }

    @Test
    void addGraphsTestSqlException() throws SQLException {
        when(mockDatabaseConnection.selectQuery(anyString(), any())).thenReturn(mockedResultSet);
        doThrow(SQLException.class).when(mockDatabaseConnection).executeUpdate(anyString(), any());
        when(mockedResultSet.next()).thenReturn(true);
        assertThrows(DatabaseException.class, () -> {
            sut.addGraph(1, 1, "linechart", "Erik");
        });
        verify(mockDatabaseConnection).executeUpdate(anyString(), any());
    }

    @Test
    void deleteGraphsTest() throws DatabaseException, SQLException {
        when(mockDatabaseConnection.selectQuery(anyString(), any())).thenReturn(mockedResultSet);
        doThrow(new SQLException()).when(mockDatabaseConnection).executeUpdate(anyString(), any());
        when(mockedResultSet.next()).thenReturn(true);
        assertThrows(DatabaseException.class, () -> {
            sut.deleteGraph(1, 1, "Erik");
        });
        verify(mockDatabaseConnection).executeUpdate(anyString(), any());
    }

    @Test
    void addGraph() throws SQLException {
        when(mockDatabaseConnection.selectQuery(anyString(), any())).thenReturn(mockedResultSet);
        when(mockedResultSet.next()).thenReturn(true).thenReturn(false);

        sut = mock(GraphDAO.class);

        sut.addGraph(tabID, graphID, graphType, "Erik");
        verify(sut).addGraph(tabID, graphID, graphType, "Erik");
    }

    @Test
    void deleteGraph() throws SQLException {
        when(mockDatabaseConnection.selectQuery(anyString(), any())).thenReturn(mockedResultSet);
        when(mockedResultSet.next()).thenReturn(true).thenReturn(false);

        sut = mock(GraphDAO.class);

        sut.deleteGraph(tabID, graphID, "Erik");
        verify(sut).deleteGraph(tabID, graphID, "Erik");
    }
}
