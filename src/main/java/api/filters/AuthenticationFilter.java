package api.filters;

import api.annotations.Secured;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter{

    /*private ILoginService m_LoginService;
    private IUserService m_UserService;

    public AuthenticationFilter(){
        m_LoginService = new LoginService();
        m_UserService = new UserService();
    }*/

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {

        String authorizationHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        String sessionId = authorizationHeader.substring("Bearer".length()).trim();

        try {
            validateToken(sessionId);

        } catch (Exception exception) {
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private void validateToken(String sessionId) throws Exception{
        /*if (!m_LoginService.sessionIdIsValid(sessionId)){
            throw new Exception("Invalid session id!");
        }*/
    }
}
