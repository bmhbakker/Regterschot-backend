package nl.aim.regterschotracing.presentation.resources;

import nl.aim.regterschotracing.domain.services.SensorService;
import nl.aim.regterschotracing.presentation.dto.graphs.GraphTypeDTO;
import nl.aim.regterschotracing.presentation.dto.graphs.SensorWithGraphDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SensorWithGraphResourceTest {

  @Mock
  private SensorWithGraphResource sut;

  @InjectMocks
  SensorService sensorService;

  String token;

  List<SensorWithGraphDTO> sensorDTOList;
  List<GraphTypeDTO> graphTypeDTOList;

  @BeforeEach
  public void setup() {
    sut = new SensorWithGraphResource();
    sensorService = mock(SensorService.class);

    sut.setSensorService(sensorService);

    token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjo0MiwidXNlcm5hbWUiOiJFcmlrIn0.FA26z63uwWWOfOM8EHfdBiYw6DzcfAF6EJ4YzZiQ-q0";

    graphTypeDTOList = new ArrayList<>();

    SensorWithGraphDTO sensorDTO = new SensorWithGraphDTO(1, "Oil", graphTypeDTOList);
    SensorWithGraphDTO sensorDTO2 = new SensorWithGraphDTO(2, "Not Oil", graphTypeDTOList);
    sensorDTOList = new ArrayList<>();
    sensorDTOList.add(sensorDTO);
    sensorDTOList.add(sensorDTO2);
  }

  @Test
  void getGraphDataWithGoodJWT() {

    var http_code = 200;

    var testValue = sut.getGraphData(token);

    assertEquals(http_code, testValue.getStatus());
  }

  @Test
  void getGraphDataWithBadJWT() {
    token = "WrongToken";

    var http_code = 403;

    var testValue = sut.getGraphData(token);

    assertEquals(http_code, testValue.getStatus());
  }

  @Test
  void returnsAllSensors() {
    when(sensorService.getAllSensors()).thenReturn(sensorDTOList);
    List<SensorWithGraphDTO> expectedResult = sensorDTOList;
    List actualDTO = (List) sut.getGraphData(token).getEntity();

    assertEquals(expectedResult.getClass(), sut.getGraphData(token).getEntity().getClass());
    assertEquals(expectedResult.get(0), actualDTO.get(0));
    assertEquals(expectedResult.get(1), actualDTO.get(1));
  }
}
