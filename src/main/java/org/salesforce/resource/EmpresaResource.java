package org.salesforce.resource;

import org.salesforce.entities.user.Empresa;
import org.salesforce.repositories.EmpresaRepository;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("empresa")
public class EmpresaResource {
    public EmpresaRepository empresaRepository;

    public EmpresaResource() {
        empresaRepository = new EmpresaRepository();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Empresa> readAll() {
        return empresaRepository.reedAll();
    }

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") int id) {
        var empresa = empresaRepository.findById(id);
        return empresa.isPresent() ?
                Response.ok(empresa.get()).build() :
                Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/cnpj/{cnpj}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByCnpj(@PathParam("cnpj") String cnpj) {
        var empresa = empresaRepository.findByCnpj(cnpj.replaceAll("[^0-9]", ""));
        return empresa.isPresent() ?
                Response.ok(empresa.get()).build() :
                Response.status(Response.Status.NOT_FOUND).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Empresa empresa) {
        try {
            empresaRepository.create(empresa);
            return Response.status(Response.Status.CREATED).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }


    @PUT
    @Path("/id/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, Empresa empresa){
        try{
            empresaRepository.update(id, empresa);
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        catch(IllegalArgumentException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/id/{id}")
    public Response deleteById(@PathParam("id") int id){
        try {
            empresaRepository.deleteById(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        catch (IllegalArgumentException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/cnpj/{cnpj}")
    public Response deleteByCnpj(@PathParam("cnpj") String cnpj){
        try {
            empresaRepository.deleteByCnpj(cnpj.replaceAll("[^0-9]", ""));
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        catch (IllegalArgumentException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
