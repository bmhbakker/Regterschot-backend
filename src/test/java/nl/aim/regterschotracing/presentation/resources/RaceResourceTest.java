package nl.aim.regterschotracing.presentation.resources;

import nl.aim.regterschotracing.domain.services.RaceService;

import nl.aim.regterschotracing.presentation.dto.RaceDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RaceResourceTest {

    @Mock
    private RaceResource sut;

    @InjectMocks
    RaceService raceService;

    String token;
    List<RaceDTO> raceDTOList;

    @BeforeEach
    public void setup() {
        sut = new RaceResource();
        raceService = mock(RaceService.class);
        token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjo0MiwidXNlcm5hbWUiOiJFcmlrIn0.FA26z63uwWWOfOM8EHfdBiYw6DzcfAF6EJ4YzZiQ-q0";
        sut.setRaceService(raceService);
        RaceDTO raceDTO = new RaceDTO(1, "BMW Race", "2022-02-02");
        RaceDTO raceDTO2 = new RaceDTO(2, "BMW R2ace", "222022-02-02");
        raceDTOList = new ArrayList<>();
        raceDTOList.add(raceDTO);
        raceDTOList.add(raceDTO2);
    }

    @Test
    void getRacesWithGoodJWT() {
        int http_code = 200;

        when(raceService.getAllRaces()).thenReturn(raceDTOList);
        int testValue = sut.getAllRaces(token).getStatus();


        assertEquals(http_code, testValue);
    }

    @Test
    void returnsAllRaces() {
        //Arrange

        when(raceService.getAllRaces()).thenReturn(raceDTOList);
        List<RaceDTO> expectedResult = raceDTOList;
        List actualDTO = (List) sut.getAllRaces(token).getEntity();
        //Act

        //Assert
        assertEquals(expectedResult.getClass(), sut.getAllRaces(token).getEntity().getClass());
        assertEquals(expectedResult.get(0), actualDTO.get(0));
    }

    @Test
    void getRacesWithBadJWT() {
        //Arrange
        token = "WrongToken";
        var http_code = 403;
        //Act
        var testValue = sut.getAllRaces(token);
        //Assert
        assertEquals(http_code, testValue.getStatus());
    }
}
