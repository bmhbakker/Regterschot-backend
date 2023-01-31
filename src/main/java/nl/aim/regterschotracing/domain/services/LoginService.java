package nl.aim.regterschotracing.domain.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.inject.Inject;
import nl.aim.regterschotracing.datasource.dao.UserDAO;
import nl.aim.regterschotracing.domain.exceptions.LoginCredentialsIncorrectException;
import nl.aim.regterschotracing.presentation.dto.login.LoginRequestDTO;
import nl.aim.regterschotracing.presentation.dto.login.LoginResponseDTO;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controls all the data of login.
 */
public class LoginService {
  private LoginResponseDTO loginResponseDTO;
  private UserDAO userDAO;
  private Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id, 16, 32);
  private static final String ERRORMESSAGE = "Incorrect username or password.";
  private static final Logger LOGGER = Logger.getLogger(LoginService.class.getName());

  /**
   * Checks if user used a valid password with username.
   *
   * @param user List with user credentials.
   */
  public LoginResponseDTO   checkLogin(LoginRequestDTO user) {

    String username = user.getUsername();
    if (username.isEmpty()) {
      throw new LoginCredentialsIncorrectException(ERRORMESSAGE);
    }
    String jwt = createJWTtoken(username);

    loginResponseDTO.setUser(username);
    loginResponseDTO.setToken(jwt);

        LoginRequestDTO users;
        String requestedUser = user.getUsername();
        try {
            users = userDAO.getUser(requestedUser);
            if(!Objects.isNull(users)) {
                if (!argon2.verify(users.getPassword(), user.getPassword().toCharArray()) || users.getPassword().isEmpty()) {
                    throw new LoginCredentialsIncorrectException(ERRORMESSAGE);
                }
            } else{
                throw new LoginCredentialsIncorrectException(ERRORMESSAGE);
            }
        } catch (LoginCredentialsIncorrectException e) {
            LOGGER.log(Level.SEVERE,ERRORMESSAGE,e);
            throw new LoginCredentialsIncorrectException(ERRORMESSAGE);
        }
        return loginResponseDTO;
    }

  public String createJWTtoken(String username) {

    return JWT.create()
      .withClaim("username", username)
      .withClaim("role", 42)
      .sign(Algorithm.HMAC256(username));
  }

  /**
   * Assigns userDAO.
   * Sets a user data acces object.
   *
   * @param userDAO Should be a UserDAO type.
   */
  @Inject
  public void setUserDAO(UserDAO userDAO) {
    this.userDAO = userDAO;
  }

  @Inject
  public void setLoginResponseDTO(LoginResponseDTO loginResponseDTO) {
    this.loginResponseDTO = loginResponseDTO;
  }
}
