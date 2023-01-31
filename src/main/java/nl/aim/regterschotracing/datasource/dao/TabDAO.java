package nl.aim.regterschotracing.datasource.dao;

import jakarta.inject.Inject;
import nl.aim.regterschotracing.datasource.databaseutil.DatabaseConnection;


import nl.aim.regterschotracing.domain.exceptions.DatabaseException;
import nl.aim.regterschotracing.domain.services.GraphService;
import nl.aim.regterschotracing.presentation.dto.TabDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Fetches all the tabs per user specific from the database.
 * Uses the class DatabaseConnection to get connection with the database.
 */
public class TabDAO {
    private GraphService graphService;
    private DatabaseConnection databaseConnection;
    private UserDAO userDAO;
    private static final Logger LOGGER = Logger.getLogger(TabDAO.class.getName());

    /**
     * Fetches the tab data per user from the database using the DatabaseConnection class.
     *
     * @param user Name of the user.
     * @return list of TabsDTO.
     */
    public List<TabDTO> getUserTabs(String user) {
        List<TabDTO> tabs = new ArrayList<>();
        try {
            String query = "SELECT t.TabName, t.TabID FROM Tab t JOIN User u ON u.UserID = t.UserID Where u.Username = ?";
            Object[] parameters = {user};
            ResultSet getTabsResult = databaseConnection.selectQuery(query, parameters);
            addResultSetToDTO(tabs, getTabsResult);

        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            try {
                databaseConnection.closeConnection();
            } catch (SQLException exception) {
                LOGGER.log(Level.SEVERE, "error closing connection", exception);
            }
        }


        return tabs;
    }

    public void deleteTab(int tabId, String username) {
        String query = "DELETE FROM Tab t1 WHERE tabID = (SELECT tabID FROM (SELECT tabID FROM Tab t2 INNER JOIN User u ON t2.UserID = u.UserID WHERE Username = ? LIMIT ?,1) t3)";
        Object[] parameters = {username,tabId};
        try {
            databaseConnection.executeUpdate(query, parameters);
        } catch (SQLException exception) {
            throw new DatabaseException(exception);
        }
    }

    public void createTab(String username, String tabName, int raceID) {
        try {
            int userID = userDAO.getUserID(username);
            String query = "INSERT INTO Tab(Tabname, RaceID, UserID) VALUES (?,?,?)";
            Object[] parameters = {tabName, raceID, userID};
            databaseConnection.executeUpdate(query, parameters);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            try {
                databaseConnection.closeConnection();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "error closing connection", e);
            }
        }
    }

    private void addResultSetToDTO(List<TabDTO> tabs, ResultSet getTabsResult) throws SQLException {
        while (getTabsResult.next()) {
            TabDTO tabDTO = new TabDTO();
            tabDTO.setTabId(getTabsResult.getInt("tabId"));
            tabDTO.setName(getTabsResult.getString("tabName"));
            tabDTO.setGraphsDTO(graphService.getAll(getTabsResult.getInt("tabId")));
            tabs.add(tabDTO);
        }
    }


    /**
     * Assigns graphService.
     * Sets the graph service
     *
     * @param graphService Should be a graphService type.
     */
    @Inject
    public void setGraphService(GraphService graphService) {
        this.graphService = graphService;
    }

    /**
     * Assings databaseconnection.
     * Sets the database connection.
     *
     * @param databaseConnection Should be a DatabaseConnection type.
     */
    @Inject
    public void setDatabaseConnection(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Inject
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
