package nl.aim.regterschotracing.domain.services;

import nl.aim.regterschotracing.datasource.dao.SensorWithGraphDAO;
import nl.aim.regterschotracing.presentation.dto.graphs.GraphTypeDTO;
import nl.aim.regterschotracing.presentation.dto.graphs.SensorWithGraphDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SensorServiceTest {
  @Mock
  public SensorService sut;
  @InjectMocks
  public SensorWithGraphDAO sensorWithGraphDAO;
  @Mock
  public SensorWithGraphDTO sensorWithGraphDTO;

  List<GraphTypeDTO> graphTypeDTOList;

  @BeforeEach
  public void setup() {
    sut = new SensorService();
    sensorWithGraphDAO = Mockito.mock(SensorWithGraphDAO.class);
    sut.setSensorWithGraphDAO(sensorWithGraphDAO);

    graphTypeDTOList = new ArrayList<>();
    sensorWithGraphDTO = new SensorWithGraphDTO(1, "Oil", graphTypeDTOList);
  }

  @Test
  void getAllSensors() {
    List<SensorWithGraphDTO> mockedData = new ArrayList<>();

    mockedData.add(sensorWithGraphDTO);

    Mockito.when(sensorWithGraphDAO.getSensorLinkedWithGraphs()).thenReturn(mockedData);

    assertEquals(sut.getAllSensors().get(0), sensorWithGraphDTO);
  }
}
