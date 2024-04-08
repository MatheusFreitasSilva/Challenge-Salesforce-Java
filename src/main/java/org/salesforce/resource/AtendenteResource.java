package org.salesforce.resource;

import org.salesforce.entities.user.Atendente;
import org.salesforce.repositories.AtendenteRepository;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("atendente")
public class AtendenteResource {
    public AtendenteRepository atendenteRepository;

    public AtendenteResource(){
        atendenteRepository = new AtendenteRepository();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Atendente> readAll() {
        return atendenteRepository.reedAll();
    }

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") int id) {
        var atendente = atendenteRepository.findById(id);
        return atendente.isPresent() ?
                Response.ok(atendente.get()).build() :
                Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/cnpj/{cpf}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("cpf") String cpf) {
        var atendente = atendenteRepository.findByCpf(cpf.replaceAll("[^0-9]", ""));
        return atendente.isPresent() ?
                Response.ok(atendente.get()).build() :
                Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Atendente atendente) {
        try {
            atendenteRepository.create(atendente);
            return Response.status(Response.Status.CREATED).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, Atendente atendente){
        try{
            atendenteRepository.update(id, atendente);
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        catch(IllegalArgumentException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/cnpj/{cpf}")
    public Response delete(@PathParam("cpf") String cpf){
        try {
            atendenteRepository.deleteByCpf(cpf.replaceAll("[^0-9]", ""));
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        catch (IllegalArgumentException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/id/{id}")
    public Response delete(@PathParam("id") int id){
        try {
            atendenteRepository.deleteById(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        catch (IllegalArgumentException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    @GET
    @Path("/getIdByCpf/{cpf}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIdByCpf(@PathParam("cpf") String cpf) {
        var cliente_id = atendenteRepository.findIdByCpf(cpf.replaceAll("[^0-9]", ""));
        if (cliente_id != 0) {
            return Response.ok(cliente_id).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
