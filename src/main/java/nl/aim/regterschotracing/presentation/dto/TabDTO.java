package nl.aim.regterschotracing.presentation.dto;

import java.util.List;

/**
 * Data transfer object that carries tab data between classes.
 */
public class TabDTO {
  String name;
  int tabId;
  List<GraphsDTO> graphsDTO;

  /**
   * Gets the name of a tab.
   *
   * @return this tab name.
   */
  public String getName() {
    return name;
  }

  /**
   * Changes or sets the name of a tab.
   *
   * @param name Should be a tab name.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the id of a tab.
   *
   * @return this tab id.
   */
  public int getTabId() {
    return tabId;
  }

  /**
   * Changes or sets the id of a tab.
   *
   * @param tabId Should be a tab id.
   */
  public void setTabId(int tabId) {
    this.tabId = tabId;
  }

  /**
   * Gets a list of GraphDTO.
   *
   * @return list with a graph id, graph name and tab id.
   */
  public List<GraphsDTO> getGraphsDTO() {
    return graphsDTO;
  }

  /**
   * Changes or sets a list of GraphsDTO
   *
   * @param graphsDTO Should be a list with a graph id, graph name and tab id.
   */
  public void setGraphsDTO(List<GraphsDTO> graphsDTO) {
    this.graphsDTO = graphsDTO;
  }
}
