package api;

import api.annotations.Secured;
import dto.LogbookEntryPutModel;
import model.LogbookEntry;
import service.LogbookEntryService;
import service.interfaces.ILogbookEntryService;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/logbookentry")
public class LogbookEntryResource {

    private ILogbookEntryService m_LogbookEntryService;

    public LogbookEntryResource(){
        m_LogbookEntryService = new LogbookEntryService();
    }

    @PUT
    @Secured
    @Path("/add")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addLogbookEntry(LogbookEntryPutModel model){
        try{
            m_LogbookEntryService.addLogbookEntry(model.toLogbookEntry());

            return Response.status(200).build();
        } catch (Exception exception){
            return Response.status(500).entity(exception.getMessage()).build();
        }

    }

    @PUT
    @Secured
    @Path("/update")
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateLogbookEntry(LogbookEntryPutModel model){
        try{
            m_LogbookEntryService.updateLogbookEntry(model.toLogbookEntry());

            return Response.status(200).build();
        } catch (Exception exception){
            return Response.status(500).entity(exception.getMessage()).build();
        }
    }

    @DELETE
    @Secured
    @Path("/delete/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response deleteLogbookEntry(@PathParam("id")int entryId){
        try{
            m_LogbookEntryService.deleteLogbookEntry(entryId);

            return Response.status(200).build();
        } catch (Exception exception){
            return Response.status(500).entity(exception.getMessage()).build();
        }
    }

    @GET
    @Secured
    @Path("user/{id}")
    @Produces("application/json")
    public Response getLogbookEntriesByUserId(@PathParam("id") int id) {
        try{
            List<LogbookEntry> entries = m_LogbookEntryService.getLogbookEntriesByUserId(id);

            return Response.status(200).entity(getEntriesAsJson(entries)).build();
        } catch (Exception exception){
            return Response.status(500).entity(exception.getMessage()).build();
        }
    }

    private JsonArray getEntriesAsJson(List<LogbookEntry> entries){
        JsonArrayBuilder builder = Json.createArrayBuilder();

        for (LogbookEntry entry : entries){
            builder.add(entry.toJson());
        }

        return builder.build();
    }
}
