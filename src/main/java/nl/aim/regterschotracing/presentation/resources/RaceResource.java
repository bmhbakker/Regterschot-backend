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

import nl.aim.regterschotracing.domain.services.RaceService;

import java.util.logging.Level;
import java.util.logging.Logger;


@Path("/overview")
public class RaceResource {
    private RaceService raceService;
    private static final Logger LOGGER = Logger.getLogger(RaceResource.class.getName());

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRaces(@QueryParam("token") String token) {
        try {
            JWT.decode(token);
            return Response.ok(raceService.getAllRaces()).build();
        }catch (JWTDecodeException e){
            LOGGER.log(Level.SEVERE, "JWT token is not valid", e);
            return Response.status(403).entity("The JWT token isn't valid.").type("text/plain").build();
        }

    }

    @Inject
    public void setRaceService(RaceService raceService) {
        this.raceService = raceService;
    }
}
