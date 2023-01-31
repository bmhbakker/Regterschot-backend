package nl.aim.regterschotracing.datasource.dao;

import jakarta.inject.Inject;
import nl.aim.regterschotracing.datasource.databaseutil.DatabaseConnection;
import nl.aim.regterschotracing.domain.exceptions.DatabaseException;
import nl.aim.regterschotracing.presentation.dto.graphs.GraphTypeDTO;
import nl.aim.regterschotracing.presentation.dto.graphs.SensorWithGraphDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Fetches the sensor and graph data from the database.
 * Uses the class DatabaseConnection to get connection with the database.
 */
public class SensorWithGraphDAO {
    ResultSet resultSensors = null;
    ResultSet resultGraphs = null;
    private DatabaseConnection databaseConnection;
    private static final Logger LOGGER = Logger.getLogger(SensorWithGraphDAO.class.getName());
    List<GraphTypeDTO> graphTypeDTOList;

    /**
     * Fetches the data of every sensor and the graph data which are linked to the sensor from database using the DatabaseConnection class.
     *
     * @return list of SensorWithGraphDTO.
     */
    public List<SensorWithGraphDTO> getSensorLinkedWithGraphs() {
        List<SensorWithGraphDTO> sensorWithGraphDTOS = new ArrayList<>();
        try {
            resultSensors = databaseConnection.selectQuery("SELECT * FROM Sensor", new Object[0]);
            while (resultSensors.next()) {
                graphTypeDTOList = new ArrayList<>();
                getGraphData();
                sensorWithGraphDTOS.add(new SensorWithGraphDTO(resultSensors.getInt("SensorID"), resultSensors.getString("SensorName"), graphTypeDTOList));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            try {
                databaseConnection.closeConnection();
            } catch (SQLException exception) {
                LOGGER.log(Level.SEVERE, "Error closing connection", exception);
            }
        }
        return sensorWithGraphDTOS;
    }

    public void getGraphData() throws SQLException {
        graphTypeDTOList = new ArrayList<>();
        Object[] parameters = {resultSensors.getInt("SensorID")};
        resultGraphs = databaseConnection.selectQuery("SELECT GraphID, GraphType FROM Graph g inner join Sensor s on g.SensorID = s.SensorID where g.SensorID = ?", parameters);
        while (resultGraphs.next()) {
            graphTypeDTOList.add(new GraphTypeDTO(resultGraphs.getString("GraphType"), (resultGraphs.getInt("GraphID"))));
        }
        if (graphTypeDTOList.isEmpty()) {
            graphTypeDTOList.add(new GraphTypeDTO(resultSensors.getString("GraphType"), -1));
        }
    }

    @Inject
    public void setDatabaseConnection(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }
}
