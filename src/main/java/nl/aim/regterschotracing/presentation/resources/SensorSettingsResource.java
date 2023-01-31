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
import nl.aim.regterschotracing.datasource.dao.GaugeGraphSettingsDAO;

import java.util.logging.Level;
import java.util.logging.Logger;


@Path("/sensorSettings")
public class SensorSettingsResource {
    private GaugeGraphSettingsDAO gaugeGraphSettingsDAO;
    private static final Logger LOGGER = Logger.getLogger(SensorSettingsResource.class.getName());

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSettings(@QueryParam("token") String token, @QueryParam("sensor") String sensor) {
        try {
            JWT.decode(token);
            return Response.ok(gaugeGraphSettingsDAO.getSensorSettings(sensor)).build();
        } catch (JWTDecodeException e) {
            LOGGER.log(Level.SEVERE, "JWT token is not valid", e);
            return Response.status(403).entity("The JWT token isn't valid.").type("text/plain").build();
        }
    }

    @Inject
    public void setGaugeGraphSettingsDAO(GaugeGraphSettingsDAO gaugeGraphSettingsDAO) {
        this.gaugeGraphSettingsDAO = gaugeGraphSettingsDAO;
    }
}

