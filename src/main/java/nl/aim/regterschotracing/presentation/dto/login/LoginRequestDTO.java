package nl.aim.regterschotracing.presentation.dto.login;

/**
 * Data transfer object that carries data to try and login between classes.
 */
public class LoginRequestDTO {

  private String username;
  private String password;

  /**
   * Gets the username of a user.
   *
   * @return this username.
   */
  public String getUsername() {
    return username;
  }

  /**
   * Changes or sets the username of a user.
   *
   * @param username Should be a name.
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Gets the password of a user.
   *
   * @return this password.
   */
  public String getPassword() {
    return password;
  }

  /**
   * Changes or sets the password of a user.
   *
   * @param password Should be a password.
   */
  public void setPassword(String password) {
    this.password = password;
  }

}
