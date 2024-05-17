package org.salesforce.resource;

import org.salesforce.entities.logs.LogsAcesso;
import org.salesforce.repositories.LogsAcessoRepository;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * Classe que representa o recurso REST para manipulação de logs de acesso.
 */
@Path("logsAcesso")
public class LogsAcessoResource {

    /**
     * Repositório de logs de acesso para acesso aos dados.
     */
    public LogsAcessoRepository logsAcessoRepository;

    /**
     * Repositório de logs de acesso para acesso aos dados.
     */

    public LogsAcessoResource(){
        logsAcessoRepository = new LogsAcessoRepository();
    }

    /**
     * Obtém todos os logs de acesso.
     *
     * @return Lista de logs de acesso no formato JSON.
     */

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<LogsAcesso> readAll() {
        return logsAcessoRepository.readAll();
    }

    /**
     * Obtém um log de acesso pelo seu ID.
     *
     * @param id O ID do log de acesso.
     * @return Resposta com o log de acesso no formato JSON se encontrado, caso contrário, retorna NOT_FOUND.
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") int id) {
        var logsAcesso = logsAcessoRepository.findById(id);
        return logsAcesso.isPresent() ?
                Response.ok(logsAcesso.get()).build() :
                Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * Cria um novo log de acesso.
     *
     * @param logsAcesso O log de acesso a ser criado no formato JSON.
     * @return Resposta indicando sucesso (CREATED) ou erro (BAD_REQUEST) com mensagem.
     */
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

    /**
     * Atualiza um log de acesso pelo seu ID.
     *
     * @param id          O ID do log de acesso a ser atualizado.
     * @param logsAcesso  O log de acesso com os novos dados no formato JSON.
     * @return Resposta indicando sucesso (NO_CONTENT) ou erro (BAD_REQUEST) com mensagem.
     */
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

    /**
     * Exclui um log de acesso pelo seu ID.
     *
     * @param id O ID do log de acesso a ser excluído.
     * @return Resposta indicando sucesso (NO_CONTENT) ou erro (BAD_REQUEST) com mensagem.
     */
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
