package nl.aim.regterschotracing.presentation.resources;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.aim.regterschotracing.domain.services.SensorService;

import java.util.logging.Level;
import java.util.logging.Logger;


@Path("/sensors")
public class SensorWithGraphResource {

  private SensorService sensorService;
  private static final Logger LOGGER = Logger.getLogger(SensorWithGraphResource.class.getName());


  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getGraphData(@QueryParam("token") String token) {
    try {
      JWT.decode(token);
      return Response.ok(sensorService.getAllSensors()).build();
    } catch (JWTDecodeException e) {
      LOGGER.log(Level.SEVERE, "The JWT token is not valid", e);
      return Response.status(403).entity("The JWT token is not valid.").type("text/plain").build();
    }
  }

  @Inject
  public void setSensorService(SensorService sensorService) {
    this.sensorService = sensorService;
  }
}
