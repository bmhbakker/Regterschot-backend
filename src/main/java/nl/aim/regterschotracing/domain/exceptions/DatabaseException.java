package nl.aim.regterschotracing.domain.exceptions;

import java.sql.SQLException;

/**
 * Thrown when database query was not correct or database connection not succesful.
 * Gives error while running the code.
 */
public class DatabaseException extends RuntimeException {

  /**
   * This exception is used for giving a response code when a database error occures.
   * @param e this is the error code that the SQLException gives.
   */
  public DatabaseException(SQLException e) {
    super(e);
  }
}
