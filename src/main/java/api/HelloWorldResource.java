package api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/helloworld")
public class HelloWorldResource {

    @GET
    public String helloWorld(){
        return "hello world";
    }
}
