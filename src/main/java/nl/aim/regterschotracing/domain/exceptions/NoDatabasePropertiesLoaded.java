package nl.aim.regterschotracing.domain.exceptions;

/**
 * Thrown when database properties are not loaded.
 * Gives error while running the code.
 */
public class NoDatabasePropertiesLoaded extends RuntimeException {

  /**
   * Sends message given in the parameter to user.
   * Used when database properties are not succesfully loaded.
   *
   * @param message Message that the user will see when the exception is called.
   */
  public NoDatabasePropertiesLoaded(String message) {
    super(message);
  }

}
