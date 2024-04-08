package org.salesforce.resource;

import org.salesforce.entities.logs.LogsAcesso;
import org.salesforce.repositories.LogsAcessoRepository;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("logsAcesso")
public class LogsAcessoResource {
    public LogsAcessoRepository logsAcessoRepository;

    public LogsAcessoResource(){
        logsAcessoRepository = new LogsAcessoRepository();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<LogsAcesso> readAll() {
        return logsAcessoRepository.readAll();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") int id) {
        var logsAcesso = logsAcessoRepository.findById(id);
        return logsAcesso.isPresent() ?
                Response.ok(logsAcesso.get()).build() :
                Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(LogsAcesso logsAcesso) {
        try {
            logsAcessoRepository.create(logsAcesso);
            return Response.status(Response.Status.CREATED).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, LogsAcesso logsAcesso){
        try{
            logsAcessoRepository.update(id, logsAcesso);
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        catch(IllegalArgumentException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") int id){
        try {
            logsAcessoRepository.deleteById(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        catch (IllegalArgumentException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
