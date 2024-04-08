package org.salesforce.resource;

import org.salesforce.entities.user.Cliente;
import org.salesforce.repositories.ClienteRepository;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("cliente")
public class ClienteResource {
    public ClienteRepository clienteRepository;

    public ClienteResource(){
        clienteRepository = new ClienteRepository();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cliente> readAll() {
        return clienteRepository.reedAll();
    }

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") int id) {
        var cliente = clienteRepository.findById(id);
        return cliente.isPresent() ?
                Response.ok(cliente.get()).build() :
                Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/cpf/{cpf}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("cpf") String cpf) {
        var cliente = clienteRepository.findByCpf(cpf.replaceAll("[^0-9]", ""));
        return cliente.isPresent() ?
                Response.ok(cliente.get()).build() :
                Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Cliente cliente) {
        try {
            clienteRepository.create(cliente);
            return Response.status(Response.Status.CREATED).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/id/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, Cliente cliente){
        try{
            clienteRepository.update(id, cliente);
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        catch(IllegalArgumentException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("{empresa_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateIdEmpresa(@PathParam("empresa_id") int empresa_id, Cliente cliente){
        try{
            clienteRepository.updateIdEmpresa(empresa_id, cliente);
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        catch(IllegalArgumentException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/cpf/{cpf}")
    public Response delete(@PathParam("cpf") String cpf){
        try {
            clienteRepository.deleteByCpf(cpf.replaceAll("[^0-9]", ""));
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
            clienteRepository.deleteById(id);
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
        var cliente_id = clienteRepository.findIdByCpf(cpf.replaceAll("[^0-9]", ""));
        if (cliente_id != 0) {
            return Response.ok(cliente_id).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
