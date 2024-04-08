package org.salesforce.resource;

import org.salesforce.entities.logs.LogsComunicacao;
import org.salesforce.repositories.LogsComunicacaoRepository;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("logsComunicacao")
public class LogsComunicacaoResource {
    public LogsComunicacaoRepository logsComunicacaoRepository;

    public LogsComunicacaoResource(){
        logsComunicacaoRepository = new LogsComunicacaoRepository();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<LogsComunicacao> readAll() {
        return logsComunicacaoRepository.readAll();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") int id) {
        var logsComunicacao = logsComunicacaoRepository.findById(id);
        return logsComunicacao.isPresent() ?
                Response.ok(logsComunicacao.get()).build() :
                Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(LogsComunicacao logsComunicacao) {
        try {
            logsComunicacaoRepository.create(logsComunicacao);
            return Response.status(Response.Status.CREATED).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, LogsComunicacao logsComunicacao){
        try{
            logsComunicacaoRepository.update(id, logsComunicacao);
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
            logsComunicacaoRepository.deleteById(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        catch (IllegalArgumentException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

}
