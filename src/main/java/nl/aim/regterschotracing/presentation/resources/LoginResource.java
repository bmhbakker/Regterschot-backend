package nl.aim.regterschotracing.presentation.resources;


import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.aim.regterschotracing.domain.services.LoginService;
import nl.aim.regterschotracing.presentation.dto.login.LoginRequestDTO;


@Path("/login")
public class LoginResource {
    private LoginService loginService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequestDTO user) {
        return Response.ok(loginService.checkLogin(user)).build();
    }

    @Inject
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

}   