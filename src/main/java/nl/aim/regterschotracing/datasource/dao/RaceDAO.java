package nl.aim.regterschotracing.datasource.dao;

import jakarta.inject.Inject;

import nl.aim.regterschotracing.datasource.databaseutil.DatabaseConnection;
import nl.aim.regterschotracing.domain.exceptions.DatabaseException;
import nl.aim.regterschotracing.presentation.dto.RaceDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Fetches race data from all races available in the database.
 * Uses the class DatabaseConnection to get connection with the database.
 */
public class RaceDAO {
    private DatabaseConnection databaseConnection;
    private static final Logger LOGGER = Logger.getLogger(RaceDAO.class.getName());

    /**
     * Fetches the data of all races that are available in the database using the DatabaseConnection class.
     *
     * @return list of RaceDTO.
     */
    public List<RaceDTO> getAllRaces() {
        List<RaceDTO> races = new ArrayList<>();
        String query = "SELECT * FROM Race";
        try {
            ResultSet rs = databaseConnection.selectQuery(query, new Object[0]);

            while (rs.next()) {
                RaceDTO raceDTO = new RaceDTO(rs.getInt("RaceID"), rs.getString("Racename"), rs.getString("Date"));
                races.add(raceDTO);
            }
        } catch (SQLException exception) {
            throw new DatabaseException(exception);

        } finally {
            try {
                databaseConnection.closeConnection();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing connection", e);
            }
        }
        return races;
    }

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
}
