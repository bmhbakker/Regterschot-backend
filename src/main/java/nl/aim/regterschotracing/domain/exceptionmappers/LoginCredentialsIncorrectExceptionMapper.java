package nl.aim.regterschotracing.domain.exceptionmappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import nl.aim.regterschotracing.domain.exceptions.LoginCredentialsIncorrectException;

/**
 * Maps the LoginCredentialsIncorrectException into an object.
 */
@Provider
public class LoginCredentialsIncorrectExceptionMapper implements ExceptionMapper<LoginCredentialsIncorrectException> {

  /**
   * Sends Response object with status 403 when method is called.
   *
   * @param e Should be a LoginCredentialsIncorrectException exception.
   * @return Response object that sends status 403 plus message.
   */
  @Override
  public Response toResponse(LoginCredentialsIncorrectException e) {
    return Response.status(403).entity(e.getMessage()).type("text/plain").build();
  }
}
