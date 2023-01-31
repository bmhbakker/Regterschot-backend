package nl.aim.regterschotracing.datasource.dao;

import jakarta.inject.Inject;

import nl.aim.regterschotracing.datasource.databaseutil.DatabaseConnection;
import nl.aim.regterschotracing.domain.exceptions.DatabaseException;
import nl.aim.regterschotracing.presentation.dto.login.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Fetches all the user data from the database
 * Uses the class DatabaseConnection to get connection with the database.
 */
public class UserDAO {
  private static final Logger LOGGER = Logger.getLogger(UserDAO.class.getName());
  private DatabaseConnection databaseConnection;

  /**
   * Fetches all the user data linked with the given username from the database using the DatabaseConnection class.
   *
   * @param username Is the username used to get user data.
   * @return LoginRequestDTO using the data from database.
   */
  public LoginRequestDTO getUser(String username) {
    LoginRequestDTO user = null;
    String query = "SELECT * FROM User WHERE BINARY username=?";
    Object[] parameters = {username};
    try {
        ResultSet result = databaseConnection.selectQuery(query, parameters);
        while (result.next()) {
            LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
            loginRequestDTO.setUsername(result.getString("username"));
            loginRequestDTO.setPassword(result.getString("password"));
            user = loginRequestDTO;
        }

        } catch (SQLException exception) {
            throw new DatabaseException(exception);
        } finally {
            try {
                databaseConnection.closeConnection();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Connection not closed", e);
            }
        }
        return user;

  }

    /**
     *
     * @param username
     * @return userID from the database that is coupled to the username
     * @throws SQLException
     */
    public int getUserID(String username) throws SQLException {
        String query = "SELECT UserID FROM User WHERE Username = ?";
        int userID = -1;
        Object[] parameters = {username};
        ResultSet usernameResult = databaseConnection.selectQuery(query, parameters);
        while(usernameResult.next()){
            userID = usernameResult.getInt("UserID");
        }
        return userID;
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
