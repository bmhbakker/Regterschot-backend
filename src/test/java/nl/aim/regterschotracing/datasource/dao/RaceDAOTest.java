package nl.aim.regterschotracing.datasource.dao;

import nl.aim.regterschotracing.datasource.databaseutil.DatabaseConnection;
import nl.aim.regterschotracing.domain.exceptions.DatabaseException;
import nl.aim.regterschotracing.domain.services.GraphService;
import nl.aim.regterschotracing.presentation.dto.RaceDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class RaceDAOTest {

    @InjectMocks
    RaceDAO sut;


    @Mock
    DatabaseConnection mockDatabaseConnection;

    @Mock
    public RaceDTO raceDTO;
    GraphService mockGraphService;
    ResultSet mockedResultSet;

    @BeforeEach
    void setup() {
        sut = new RaceDAO();
        mockDatabaseConnection = mock(DatabaseConnection.class);
        mockedResultSet = mock(ResultSet.class);
        mockGraphService = mock(GraphService.class);
        sut.setDatabaseConnection(mockDatabaseConnection);
        raceDTO = new RaceDTO(1, "BMW Race", "2022-02-22");
    }

    @Test
    void getAllRaces() throws SQLException {
        //Arrange
        when(mockDatabaseConnection.selectQuery(anyString(), any())).thenReturn(mockedResultSet);
        when(mockedResultSet.next()).thenReturn(true).thenReturn(false);
        //Act & Assert
        assertNotNull(sut.getAllRaces());
        verify(mockDatabaseConnection).selectQuery(anyString(), any());
    }

    @Test
    void getAllRacesSqlException() throws SQLException {
        //Arrange
        when(mockDatabaseConnection.selectQuery(anyString(), any())).thenThrow(SQLException.class);
        //Act & Assert
        assertThrows(DatabaseException.class, () -> {
            sut.getAllRaces();
        });
        verify(mockDatabaseConnection).selectQuery(anyString(), any());
    }

    @Test
    void testDataAllRaces() throws SQLException {
        //Arrange
        when(mockedResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockDatabaseConnection.selectQuery(anyString(), any())).thenReturn(mockedResultSet);
        when(mockedResultSet.getInt("RaceID")).thenReturn(1);
        when(mockedResultSet.getString("Racename")).thenReturn("BMW Race");
        when(mockedResultSet.getString("Date")).thenReturn("2022-02-22");

        List<RaceDTO> expectedResult = new ArrayList<>();
        expectedResult.add(raceDTO);
//Act
        List<RaceDTO> actualResult = sut.getAllRaces();

//Assert
        assertEquals(actualResult.get(0).getId(), expectedResult.get(0).getId());
        assertEquals(actualResult.get(0).getRaceName(), expectedResult.get(0).getRaceName());
        assertEquals(actualResult.get(0).getDate(), expectedResult.get(0).getDate());
    }

}