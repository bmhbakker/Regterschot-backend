package nl.aim.regterschotracing.presentation.dto.graphs;

/**
 * Data transfer object that carries graph data between classes.
 */
public class GraphTypeDTO {
  private String graphType;
  private int graphID;

  /**
   * Constructor to make a graph data transfer object.
   *
   * @param graphType Type of graph.
   * @param graphID   Unique number to identify a graph.
   */
  public GraphTypeDTO(String graphType, int graphID) {
    this.graphType = graphType;
    this.graphID = graphID;
  }

  /**
   * Gets the type of the graph.
   *
   * @return this graph type.
   */
  public String getGraphType() {
    return graphType;
  }

  /**
   * Changes or sets the type of graph.
   *
   * @param graphType Should be a type of graph.
   */
  public void setGraphType(String graphType) {
    this.graphType = graphType;
  }

  /**
   * Gets the id of the graph.
   *
   * @return this graph id.
   */
  public int getGraphID() {
    return graphID;
  }

  /**
   * Changes or sets the id of a graph.
   *
   * @param graphID Should be an id.
   */
  public void setGraphID(int graphID) {
    this.graphID = graphID;
  }
}
