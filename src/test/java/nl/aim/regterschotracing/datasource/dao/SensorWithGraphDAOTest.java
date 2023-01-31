package nl.aim.regterschotracing.datasource.dao;


import nl.aim.regterschotracing.datasource.databaseutil.DatabaseConnection;
import nl.aim.regterschotracing.domain.exceptions.DatabaseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class SensorWithGraphDAOTest {

  @InjectMocks
  SensorWithGraphDAO sut;
  @Mock
  DatabaseConnection mockDatabaseConnection;
  ResultSet mockedResultSet;

  @BeforeEach
  void setup() {
    sut = new SensorWithGraphDAO();
    mockDatabaseConnection = mock(DatabaseConnection.class);
    mockedResultSet = mock(ResultSet.class);
    sut.setDatabaseConnection(mockDatabaseConnection);
  }

  @Test
  void getSensorLinkedWithGraphsCorrectly() throws SQLException {
    Mockito.when(mockDatabaseConnection.selectQuery(anyString(), any())).thenReturn(mockedResultSet);
    Mockito.when(mockedResultSet.next()).thenReturn(true).thenReturn(false);

    assertNotNull(sut.getSensorLinkedWithGraphs());

    verify(mockDatabaseConnection, atLeast(2)).selectQuery(anyString(), any());
  }



  @Test
  void getUserTabsSqlException() throws SQLException {
    when(mockDatabaseConnection.selectQuery(anyString(), any())).thenThrow(SQLException.class);

    assertThrows(DatabaseException.class, () -> sut.getSensorLinkedWithGraphs());

    verify(mockDatabaseConnection).selectQuery(anyString(), any());
  }
}
