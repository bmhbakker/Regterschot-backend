package nl.aim.regterschotracing.domain.services;

import jakarta.inject.Inject;
import nl.aim.regterschotracing.datasource.dao.GraphDAO;
import nl.aim.regterschotracing.presentation.dto.GraphsDTO;

import java.util.List;

/**
 * Controls the data of all the graphs.
 */
public class GraphService {
  private GraphDAO graphDAO = new GraphDAO();

  /**
   * Assigns graphDAO.
   * Sets a tab data acces object.
   *
   * @param graphDAO Should be a GraphDAO type.
   */
  @Inject
  public void setGraphDAO(GraphDAO graphDAO) {
    this.graphDAO = graphDAO;
  }

  /**
   * Gets all graph data in the database based on the id.
   *
   * @param id Should be a valid graph id.
   * @return All graph data based on the id.
   *
   *
   */
  public List<GraphsDTO> getAll(int id) {
    return graphDAO.getGraphs(id);
  }

  /**
   * delegates the deleteGraph method to graphDAO
   *
   * @param tabID ID of the tab where you want to delete the graph
   * @param graphID ID of the graph wich you want to delete
   *
   * @see GraphDAO#deleteGraph(int, int) 
   *
   */

  public void deleteGraph(int tabID, int graphID, String username) {
    graphDAO.deleteGraph(tabID, graphID,username);
  }

  /**
   * deletes the addGraph method to graphDAO
   *
   * @param tabID ID of the tab where you want to add the graph
   * @param graphID ID of the graph
   * @param graphType Type of the graph for example linegraph
   * @param username username of the logged in user
   *                  
   * @see GraphDAO#addGraph(int, int, String)
   */
    public void addGraph(int tabID, int graphID,String sensorName,String username) {
    graphDAO.addGraph(tabID,graphID,sensorName,username);
    }
}
