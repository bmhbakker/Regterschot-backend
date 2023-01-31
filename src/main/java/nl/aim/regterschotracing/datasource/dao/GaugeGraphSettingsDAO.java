package nl.aim.regterschotracing.datasource.dao;

import jakarta.inject.Inject;
import nl.aim.regterschotracing.datasource.databaseutil.DatabaseConnection;
import nl.aim.regterschotracing.domain.exceptions.DatabaseException;
import nl.aim.regterschotracing.presentation.dto.graphs.GaugeSettingsDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GaugeGraphSettingsDAO {
    ResultSet resultSettings = null;
    private DatabaseConnection databaseConnection;
    private static final Logger LOGGER = Logger.getLogger(GaugeGraphSettingsDAO.class.getName());

    public List<GaugeSettingsDTO> getSensorSettings(String sensor) {
        List<GaugeSettingsDTO> gaugeSettingsDTOS = new ArrayList<>();
        try {
            Object[] parameters = {sensor};
            resultSettings = databaseConnection.selectQuery("SELECT gs.* FROM Sensor s " +
                    "INNER JOIN GaugeSettings gs ON s.SensorID = gs.SensorID " + "WHERE s.SensorName = ?", parameters);
            while (resultSettings.next()) {
                gaugeSettingsDTOS.add(new GaugeSettingsDTO(
                        resultSettings.getInt("Min"),
                        resultSettings.getInt("Max"),
                        resultSettings.getInt("GreenTo"),
                        resultSettings.getInt("YellowTo")
                ));
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
        return gaugeSettingsDTOS;
    }

    @Inject
    public void setDatabaseConnection(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }
}
