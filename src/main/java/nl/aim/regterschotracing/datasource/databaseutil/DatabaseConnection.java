package nl.aim.regterschotracing.datasource.databaseutil;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import jakarta.inject.Inject;
import nl.aim.regterschotracing.domain.exceptions.DatabaseException;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {
    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());
    private static final int LPORT = 22;
    private static final String RHOST = "localhost";
    private static final int RPORT = 3306;

    DatabaseProperties databaseProperties = new DatabaseProperties();

    Connection dbConnection = getConnection();


    @Inject
    public DatabaseConnection(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }

    /**
     * Creates the SSH connection.
     */
    public void go() {
        String user = databaseProperties.username();
        String password = databaseProperties.password();
        String host = databaseProperties.host();
        int port = 22;
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            LOGGER.log(Level.INFO, "Establishing Connection...");
            session.connect();
            session.setPortForwardingL(LPORT, RHOST, RPORT);
            String string = String.format("localhost: %s -> %s : %d", getConnectionString(), RHOST, RPORT);
            LOGGER.log(Level.INFO, string);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "error connecting", e);
        }
    }

    /**
     * Creates the database connection.
     * Uses the SSH connection.
     *
     * @return the connection.
     */
    public Connection getConnection() {
        Connection connection = null;
        try {
            go();
            String driver = databaseProperties.driver();
            String url = "jdbc:mysql://" + RHOST + ":" + LPORT + "/";
            String db = databaseProperties.database();
            String dbUser = databaseProperties.username();
            String dbPasswd = databaseProperties.password();
            Class.forName(driver);
            connection = DriverManager.getConnection(url + db, dbUser, dbPasswd);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Class not found", e);
        }
        return connection;
    }

    /**
     * Gets the connection string to connect to the mysql database.
     *
     * @return The correct connection string.
     */
    public String getConnectionString() {
        return databaseProperties.connectionString();
    }

    /**
     * Excecutes a select query using the parameters.
     *
     * @param query      Query that needs to be excecuted.
     * @param parameters Parameters for the query.
     * @return The statement to excecute.
     * @throws SQLException When prepared statement is not succesful.
     */
    public ResultSet selectQuery(String query, Object[] parameters) throws SQLException {
        PreparedStatement stmt = dbConnection.prepareStatement(query);
        try {
            PreparedStatement finalStmt = setParameters(parameters, stmt);

            return finalStmt.executeQuery();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public ResultSet selectQuery(String query) throws SQLException {
        PreparedStatement stmt = dbConnection.prepareStatement(query);
        try {
            return stmt.executeQuery();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * Excecutes an update query using the paremeters.
     *
     * @param query      Query that needs to be excecuted.
     * @param parameters Parameters for the query.
     * @throws SQLException When prepared statement is not succesful.
     */
    public void executeUpdate(String query, Object[] parameters) throws SQLException {
        PreparedStatement stmt = dbConnection.prepareStatement(query);
        try {
            PreparedStatement finalStmt = setParameters(parameters, stmt);
            finalStmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    /**
     * Sets the paremeters for the prepared statements.
     *
     * @param parameters Are the parameters for the prepared statement.
     * @param stmt       Should be a prepared statement.
     * @return Prepared statement that needs to be excecuted.
     */
    private PreparedStatement setParameters(Object[] parameters, PreparedStatement stmt) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i] instanceof String) {
                stmt.setString(i + 1, parameters[i].toString());
            } else if (parameters[i] instanceof Integer) {
                stmt.setInt(i + 1, (Integer) parameters[i]);
            }
        }
        return stmt;
    }

    /**
     * Shuts down the database connection.
     *
     * @throws SQLException when not succesful.
     */
    public void closeConnection() throws SQLException {
        dbConnection.close();
    }

}
