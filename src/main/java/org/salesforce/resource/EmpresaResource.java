package org.salesforce.resource;

import org.salesforce.entities.user.Empresa;
import org.salesforce.repositories.EmpresaRepository;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * Classe que representa o recurso REST para manipulação de empresas.
 */
@Path("empresa")
public class EmpresaResource {

    /**
     * Classe que representa o recurso REST para manipulação de empresas.
     */
    public EmpresaRepository empresaRepository;

    /**
     * Construtor padrão que inicializa o repositório de empresas.
     */

    public EmpresaResource() {
        empresaRepository = new EmpresaRepository();
    }

    /**
     * Obtém todas as empresas.
     *
     * @return Lista de empresas no formato JSON.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Empresa> readAll() {
        return empresaRepository.reedAll();
    }

    /**
     * Obtém uma empresa pelo seu ID.
     *
     * @param id O ID da empresa.
     * @return Resposta com a empresa no formato JSON se encontrado, caso contrário, retorna NOT_FOUND.
     */
    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") int id) {
        var empresa = empresaRepository.findById(id);
        return empresa.isPresent() ?
                Response.ok(empresa.get()).build() :
                Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * Obtém uma empresa pelo seu CNPJ.
     *
     * @param cnpj O CNPJ da empresa.
     * @return Resposta com a empresa no formato JSON se encontrado, caso contrário, retorna NOT_FOUND.
     */
    @GET
    @Path("/cnpj/{cnpj}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByCnpj(@PathParam("cnpj") String cnpj) {
        var empresa = empresaRepository.findByCnpj(cnpj.replaceAll("[^0-9]", ""));
        return empresa.isPresent() ?
                Response.ok(empresa.get()).build() :
                Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * Cria uma nova empresa.
     *
     * @param empresa A empresa a ser criada no formato JSON.
     * @return Resposta indicando sucesso (CREATED) ou erro (BAD_REQUEST) com mensagem.
     */
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

    /**
     * Atualiza uma empresa pelo seu ID.
     *
     * @param id      O ID da empresa a ser atualizada.
     * @param empresa A empresa com os novos dados no formato JSON.
     * @return Resposta indicando sucesso (NO_CONTENT) ou erro (BAD_REQUEST) com mensagem.
     */
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

    /**
     * Exclui uma empresa pelo seu ID.
     *
     * @param id O ID da empresa a ser excluída.
     * @return Resposta indicando sucesso (NO_CONTENT) ou erro (BAD_REQUEST) com mensagem.
     */
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

    /**
     * Exclui uma empresa pelo seu CNPJ.
     *
     * @param cnpj O CNPJ da empresa a ser excluída.
     * @return Resposta indicando sucesso (NO_CONTENT) ou erro (BAD_REQUEST) com mensagem.
     */
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
