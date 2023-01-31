package nl.aim.regterschotracing.domain.services;

import nl.aim.regterschotracing.datasource.dao.RaceDAO;
import nl.aim.regterschotracing.presentation.dto.RaceDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class RaceServiceTest {

    @Mock
    public RaceService sut;
    @InjectMocks
    public RaceDAO raceDAO;
    @Mock
    public RaceDTO raceDTO;

    @BeforeEach
    public void setup() {
        sut = new RaceService();
        raceDAO = Mockito.mock(RaceDAO.class);
        sut.setRaceDAO(raceDAO);
        raceDTO = new RaceDTO(1, "BMW Race", "2022-05-22");
    }

    @Test
    void getAllRaces() {
        //Arrange
        List<RaceDTO> mockedData = new ArrayList<>();


        mockedData.add(raceDTO);

        //Act
        Mockito.when(raceDAO.getAllRaces()).thenReturn(mockedData);


        //Assert
        assertEquals(sut.getAllRaces().get(0), raceDTO);

    }
}
