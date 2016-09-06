package api;

import dto.Credentials;
import dto.Identity;
import service.LoginService;
import service.interfaces.ILoginService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/login")
public class LoginResource {

    private ILoginService m_LoginService;

    public LoginResource(){
        m_LoginService = new LoginService();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response login(Credentials credentials){
        Identity identity = m_LoginService.loginUser(credentials.getUsername(), credentials.getPassword());

        return Response.status(200).entity(identity).build();
    }
}
