package nl.aim.regterschotracing.presentation.dto.login;

/**
 * Data transfer object that carries data to check if a login was succesful between classes.
 */
public class LoginResponseDTO {

  private String user;
  private String token;

  /**
   * Gets the user.
   *
   * @return this user.
   */
  public String getUser() {
    return user;
  }

  /**
   * Changes or sets the user.
   *
   * @param user Should be a user.
   */
  public void setUser(String user) {
    this.user = user;
  }

  /**
   * Gets the token of the user.
   *
   * @return this token.
   */
  public String getToken() {
    return token;
  }

  /**
   * Changes or sets the token of a user.
   *
   * @param token Should be a token.
   */
  public void setToken(String token) {
    this.token = token;
  }

}
