package api;

import dto.LogbookEntryPutModel;
import service.LogbookEntryService;
import service.interfaces.ILogbookEntryService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/logbookentry")
public class LogbookEntryResource {

    private ILogbookEntryService m_LogbookEntryService;

    public LogbookEntryResource(){
        m_LogbookEntryService = new LogbookEntryService();
    }

    @PUT
    @Path("/add")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addLogbookEntry(LogbookEntryPutModel model){
        m_LogbookEntryService.addLogbookEntry(model.getUserId(), model.getEntry());

        return Response.status(200).build();
    }

    @PUT
    @Path("/update")
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateLogbookEntry(LogbookEntryPutModel model){
        m_LogbookEntryService.updateLogbookEntry(model.getUserId(), model.getEntry());

        return Response.status(200).build();
    }

    @DELETE
    @Consumes("application/json")
    @Produces("application/json")
    public Response deleteLogbookEntry(int entryId){
        m_LogbookEntryService.deleteLogbookEntry(entryId);

        return Response.status(200).build();
    }
}
