package nl.aim.regterschotracing.presentation.dto.graphs;

import java.util.List;

/**
 * Data transfer object that carries sensor data linked with graphs between classes.
 */
public class SensorWithGraphDTO {
  private int sensorId;
  private String sensorName;
  private List<GraphTypeDTO> graphTypeDTOList;

  /**
   * Constructor to make a sensor data transfer object.
   *
   * @param sensorID         Unique number to identify a sensor.
   * @param sensorName       Name of a sensor.
   * @param graphTypeDTOList List that holds data from a graph type.
   */
  public SensorWithGraphDTO(int sensorID, String sensorName, List<GraphTypeDTO> graphTypeDTOList) {
    this.sensorId = sensorID;
    this.sensorName = sensorName;
    this.graphTypeDTOList = graphTypeDTOList;
  }

  /**
   * Gets the id of a sensor.
   *
   * @return this sensor id.
   */
  public int getSensorId() {
    return sensorId;
  }

  /**
   * Changes or sets the id of a sensor.
   *
   * @param sensorId Should be an id.
   */
  public void setSensorId(int sensorId) {
    this.sensorId = sensorId;
  }

  /**
   * Gets the name of a sensor.
   *
   * @return this sensor name.
   */
  public String getSensorName() {
    return sensorName;
  }

  /**
   * Changes or sets the name for a sensor.
   *
   * @param sensorName Should be a name.
   */
  public void setSensorName(String sensorName) {
    this.sensorName = sensorName;
  }

  /**
   * Gets a list of GraphTypeDTO.
   *
   * @return list with type of graph and id of the graph.
   */
  public List<GraphTypeDTO> getGraphTypeDTOList() {
    return graphTypeDTOList;
  }

  /**
   * Changes or sets a list of GraphTypeDTO.
   *
   * @param graphTypeDTOList Should be a list with a type of graph and an id of a graph.
   */
  public void setGraphTypeDTOList(List<GraphTypeDTO> graphTypeDTOList) {
    this.graphTypeDTOList = graphTypeDTOList;
  }

}
