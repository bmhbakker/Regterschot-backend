package nl.aim.regterschotracing.domain.exceptions;

/**
 * Thrown when login credentials are not correct.
 * Gives error while running the code.
 */
public class LoginCredentialsIncorrectException extends RuntimeException {

  /**
   * Sends message given in the parameter to user.
   * Used when login credentials are incorrect.
   *
   * @param message Message that the user will see when the exception is called.
   */
  public LoginCredentialsIncorrectException(String message) {
    super(message);
  }
}
