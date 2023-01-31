package nl.aim.regterschotracing.datasource.databaseutil;

import nl.aim.regterschotracing.domain.exceptions.NoDatabasePropertiesLoaded;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Sets the values of the database connection.
 */
public class DatabaseProperties {

    private Properties properties;
    private static final Logger LOGGER =  Logger.getLogger(DatabaseProperties.class.getName());

  /**
   * Constructor to put all the values of the database connection together.
   */
  public DatabaseProperties() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,"Database properties are not loaded",e);
            throw new NoDatabasePropertiesLoaded("The database properties are not loaded op correctly.");
        }
    }


    public String connectionString() { return properties.getProperty("connectionString");}

    public String username()
    {
        return properties.getProperty("username");
    }

    public String password(){ return properties.getProperty("password"); }

    public String database() {return properties.getProperty("db");}

    public String driver() {return properties.getProperty("driver");}

    public String host() {return properties.getProperty("host");}


}
