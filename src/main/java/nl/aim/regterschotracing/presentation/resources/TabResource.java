package nl.aim.regterschotracing.presentation.resources;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.aim.regterschotracing.domain.services.TabService;


import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/tabs")
public class TabResource {
    private TabService tabService;
    private static final String JWT_TOKEN = "JWT token is not valid.";
    private static final String TEXT_TYPE = "text/plain";
    private static final String RECEIVE_NAME = "username";

    /**
     * Makes a request for a deletion in the database.
     * @param token makes sure a user is logged in to fulfill this request.
     * @param tabid defines which tab needs to be deleted
     * @return The ok status will be displayed on a successful run. The message "deleted tab" will also show up.
     * In case the user is not logged in, the method will return a 403 response, which means that the request is forbidden.
     */
    @DELETE
    @Path("{tabid}")
    public Response deleteTab(@QueryParam("token") String token, @PathParam("tabid") int tabid) {
        try {
            DecodedJWT clearToken;
            clearToken = JWT.decode(token);
            String username = clearToken.getClaim(RECEIVE_NAME).asString();
            tabService.deleteTab(tabid,username);
            System.out.println("---------------------------------------------------------------------");
            System.out.println(clearToken);
            System.out.println(tabid);
            System.out.println(username);
            System.out.println("----------------------------------------------------------------------");
            return Response.ok("Deleted tab").build();
        } catch (JWTDecodeException e) {
            LOGGER.log(Level.SEVERE, JWT_TOKEN, e);
            return Response.status(403).entity(JWT_TOKEN).type(TEXT_TYPE).build();
        }
    }

    /**
     * Gets all the tabs from the database from the user that is logged in.
     * @param token makes sure a user is logged in to fulfill this request and which tabs need to be received.
     * @return The ok status will be displayed on a successful run. It will also sow a list of all tabs received from the database.
     * In case the user is not logged in, the method will return a 403 response, which means that the request is forbidden.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTabs(@QueryParam("token") String token) {
        try {
            DecodedJWT clearToken;
            clearToken = JWT.decode(token);
            return Response.ok(tabService.getAll(clearToken.getClaim(RECEIVE_NAME).asString())).build();
        } catch (JWTDecodeException e) {
            LOGGER.log(Level.SEVERE, JWT_TOKEN, e);
            return Response.status(403).entity(JWT_TOKEN).type(TEXT_TYPE).build();
        }
    }

    /**
     * Makes a request for the creation of a new tab.
     * @param token makes sure a user is logged in to fulfill this request and gives a user to couple to the creation of the tab.
     * @param tabName will be used to give a name to the tab.
     * @param raceID will couple the tab to a race.
     * @return The ok status will be displayed on a successful run. The message "Created tab" will also show up.
     * In case the user is not logged in, the method will return a 403 response, which means that the request is forbidden.
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTab(@QueryParam("token") String token, @QueryParam("tabName") String tabName, @QueryParam("raceID") int raceID){
        try {
            DecodedJWT clearToken;
            clearToken = JWT.decode(token);
            String username = clearToken.getClaim(RECEIVE_NAME).asString();
            tabService.createTab(username, tabName, raceID);
            return Response.ok("Created tab").build();
        } catch (JWTDecodeException e) {
            LOGGER.log(Level.SEVERE, JWT_TOKEN, e);
            return Response.status(403).entity(JWT_TOKEN).type(TEXT_TYPE).build();
        }
    }
    private static final Logger LOGGER = Logger.getLogger(TabResource.class.getName());


    @Inject
    public void setTabService(TabService tabService) {
        this.tabService = tabService;
    }
}
