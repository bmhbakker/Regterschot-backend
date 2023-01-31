package nl.aim.regterschotracing.presentation.resources;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import nl.aim.regterschotracing.domain.services.GraphService;

@Path("/graphs")
public class GraphResource {
    private GraphService graphService;

    @POST
    @Path("/{tabid}")
    public Response addGraph(@PathParam("tabid") int tabID, @QueryParam("graphid") int graphID, @QueryParam("sensorName") String sensorName, @QueryParam("token") String token) {
        DecodedJWT clearToken;
        clearToken = JWT.decode(token);
        String username = clearToken.getClaim("username").asString();
        graphService.addGraph(tabID, graphID, sensorName, username);
        return Response.ok("added graph").build();
    }

    @DELETE
    @Path("/{tabid}")
    public Response deleteGraph(@PathParam("tabid") int tabID, @QueryParam("graphid") int graphID, @QueryParam("token") String token) {
        DecodedJWT clearToken;
        clearToken = JWT.decode(token);
        String username = clearToken.getClaim("username").asString();
        graphService.deleteGraph(tabID, graphID, username);
        return Response.ok("deleted graph").build();
    }

    @Inject
    public void setGraphService(GraphService graphService) {
        this.graphService = graphService;
    }
}
