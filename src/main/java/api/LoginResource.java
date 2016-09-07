package api;

import dto.Credentials;
import model.User;
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
        try{
            User user = m_LoginService.loginUser(credentials.getUsername(), credentials.getPassword());

            return Response.status(200).entity(user.toJson()).build();
        }
        catch (Exception exception){
            return Response.status(500).entity(exception.getMessage()).build();
        }
    }
}
