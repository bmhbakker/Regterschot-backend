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
class GaugeGraphSettingsDAOTest {

  @InjectMocks
  GaugeGraphSettingsDAO sut;
  @Mock
  DatabaseConnection mockDatabaseConnection;
  ResultSet mockedResultSet;

  @BeforeEach
  void setup() {
    sut = new GaugeGraphSettingsDAO();
    mockDatabaseConnection = mock(DatabaseConnection.class);
    mockedResultSet = mock(ResultSet.class);
    sut.setDatabaseConnection(mockDatabaseConnection);
  }

  @Test
  void getSensorSettingsForGauge() throws SQLException {
    when(mockDatabaseConnection.selectQuery(anyString(), any())).thenReturn(mockedResultSet);
    when(mockedResultSet.next()).thenReturn(true).thenReturn(false);

    assertNotNull(sut.getSensorSettings("Oil temperature"));

    verify(mockDatabaseConnection).selectQuery(anyString(), any());
  }

  @Test
  void getSensorSettingsForLinechart() throws SQLException {
    when(mockDatabaseConnection.selectQuery(anyString(), any())).thenReturn(mockedResultSet);
    when(mockedResultSet.next()).thenReturn(true).thenReturn(false);

    assertNotNull(sut.getSensorSettings("Motor oil level"));

    verify(mockDatabaseConnection).selectQuery(anyString(), any());
  }

  @Test
  void getSensorSettingsForGaugeException() throws SQLException {
    when(mockDatabaseConnection.selectQuery(anyString(), any())).thenThrow(SQLException.class);

    assertThrows(DatabaseException.class, () -> sut.getSensorSettings("Oil temperature"));

    verify(mockDatabaseConnection).selectQuery(anyString(), any());
  }
}
