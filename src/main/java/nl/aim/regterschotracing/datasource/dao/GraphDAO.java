package nl.aim.regterschotracing.datasource.dao;

import jakarta.inject.Inject;
import nl.aim.regterschotracing.datasource.databaseutil.DatabaseConnection;
import nl.aim.regterschotracing.domain.exceptions.DatabaseException;
import nl.aim.regterschotracing.presentation.dto.GraphsDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Fetches the graph data from the database.
 * Uses the class DatabaseConnection to get connection with the database.
 */
public class GraphDAO {
    private DatabaseConnection databaseConnection;
    private static final Logger LOGGER = Logger.getLogger(GraphDAO.class.getName());


    /**
     * Assings databaseconnection.
     * Sets database connection.
     *
     * @param databaseConnection Should be a DatabaseConnection type.
     */
    @Inject
    public void setDatabaseConnection(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    /**
     * Fetches graph data from the database using the DatabaseConnection class.
     *
     * @param id Unique number to identify the tab.
     * @return list of GrapsDTO.
     */
    public List<GraphsDTO> getGraphs(int id) {
        List<GraphsDTO> graphs = new ArrayList<>();
        try {
            String query = "SELECT tg.GraphID,s.SensorID,s.SensorName, s.graphType, tg.TabID FROM Graph tg JOIN Sensor s ON tg.SensorID = s.SensorID Where tg.TabID = ?";
            Object[] parameters = {id};
            ResultSet getTabsResult = databaseConnection.selectQuery(query, parameters);
            while (getTabsResult.next()) {
                graphs.add(setGraph(getTabsResult));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
        return graphs;

    }

    /**
     * sets the retrieved data and adds it to the graph
     *
     * @param getTabsResult the result from the select query
     * @return the graph
     */
    private GraphsDTO setGraph(ResultSet getTabsResult) throws SQLException {
        GraphsDTO graphsDTO = new GraphsDTO();
        graphsDTO.setId(getTabsResult.getInt("GraphID"));
        graphsDTO.setName(getTabsResult.getString("SensorName"));
        graphsDTO.setTabId(getTabsResult.getInt("TabID"));
        graphsDTO.setType(getTabsResult.getString("GraphType"));
        if (Objects.equals(getTabsResult.getString("GraphType"), "gauge")) {
            String gaugeSettings = "SELECT * FROM GaugeSettings gs WHERE gs.SensorID = ?";
            Object[] gaugeSettingsParameters = {getTabsResult.getString("SensorID")};
            ResultSet gaugeSettingsResult = databaseConnection.selectQuery(gaugeSettings, gaugeSettingsParameters);
            while (gaugeSettingsResult.next()) {
                graphsDTO.addSetting(gaugeSettingsResult.getInt("Min"));
                graphsDTO.addSetting(gaugeSettingsResult.getInt("Max"));
                graphsDTO.addSetting(gaugeSettingsResult.getInt("GreenTo"));
                graphsDTO.addSetting(gaugeSettingsResult.getInt("YellowTo"));
            }
        }
        return graphsDTO;
    }

    /**
     * Deletes a graph from the database using the databaseConnection class
     *
     * @param tabID    ID of the tab where you want to delete the graph
     * @param graphID  ID of the graph wich you want to delete
     * @param username the username of the user
     */
    public void deleteGraph(int tabID, int graphID, String username) {
        try {
            Object[] tabParams = {username, tabID};
            ResultSet tabIDDatabaseRS = databaseConnection.selectQuery("SELECT TabID FROM Tab t INNER JOIN User u ON t.UserID = u.UserID WHERE Username = ? LIMIT ?,1", tabParams);
            if (tabIDDatabaseRS.next()) {
                int tabIDDatabase = tabIDDatabaseRS.getInt("TabID");
                Object[] graphParams = {tabIDDatabase, graphID};

                String query = "DELETE FROM Graph g WHERE SensorID = ? AND TabID = ?";
                Object[] params = {graphID, tabIDDatabase};
                databaseConnection.executeUpdate(query, params);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            try {
                databaseConnection.closeConnection();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing connection", e);
            }

        }
    }

    /**
     * Creates a graph for a specific tab in the database using the databaseConnection class
     *
     * @param tabID     ID of the tab where you want to add the graph
     * @param graphID   ID of the graph
     * @param graphType Type of the graph for example linegraph
     */

    public void addGraph(int tabID, int graphID, String sensorName, String username) {
        try {
            Object[] sensorParams = {sensorName};
            ResultSet rs = databaseConnection.selectQuery("SELECT * FROM Sensor WHERE SensorName= ?", sensorParams);
            if (rs.next()) {
                Object[] tabParams = {username, tabID};
                ResultSet tabIdDatabaseRs = databaseConnection.selectQuery("SELECT TabID FROM Tab t INNER JOIN User u ON t.UserID = u.UserID WHERE Username = ? LIMIT ?,1", tabParams);
                int sensorID = rs.getInt("SensorID");
                if (tabIdDatabaseRs.next()) {
                    Object[] insertParams = new Object[]{sensorID, tabIdDatabaseRs.getInt("TabID")};
                    databaseConnection.executeUpdate("INSERT INTO Graph(SensorID,TabID) VALUES(?,?)", insertParams);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            try {
                databaseConnection.closeConnection();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing connection", e);
            }
        }
    }
}
