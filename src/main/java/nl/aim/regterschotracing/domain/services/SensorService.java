package nl.aim.regterschotracing.domain.services;

import jakarta.inject.Inject;
import nl.aim.regterschotracing.datasource.dao.SensorWithGraphDAO;
import nl.aim.regterschotracing.presentation.dto.graphs.SensorWithGraphDTO;

import java.util.List;

public class SensorService {
  private SensorWithGraphDAO sensorWithGraphDAO = new SensorWithGraphDAO();

  /**
   * Gets all sensors in the database.
   *
   * @return all sensors linked with graphs available.
   */
  public List<SensorWithGraphDTO> getAllSensors() {
    return sensorWithGraphDAO.getSensorLinkedWithGraphs();
  }

  /**
   * Assigns sensorWithGraphDAO.
   * Sets a sensor data acces object.
   *
   * @param sensorWithGraphDAO Should be a SensorWithGraphDAO type.
   */
  @Inject
  public void setSensorWithGraphDAO(SensorWithGraphDAO sensorWithGraphDAO) {
    this.sensorWithGraphDAO = sensorWithGraphDAO;
  }
}
