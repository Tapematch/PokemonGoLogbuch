package api;

import service.LoginService;
import service.interfaces.ILoginService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/logout")
public class LogoutResource {

    private ILoginService m_LoginService;

    public LogoutResource(){
        m_LoginService = new LoginService();
    }

    @POST
    @Path("/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response logout(@PathParam("id") int userId){
        try{
            m_LoginService.logoutUser(userId);

            return Response.status(200).build();
        }
        catch (Exception exception){
            return Response.status(500).entity(exception.getMessage()).build();
        }
    }
}
