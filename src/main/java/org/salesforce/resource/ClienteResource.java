package org.salesforce.resource;

import org.salesforce.entities.user.Cliente;
import org.salesforce.repositories.ClienteRepository;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * Classe que representa o recurso REST para manipulação de clientes.
 */
@Path("cliente")
public class ClienteResource {

    /**
     * Repositório de clientes para acesso aos dados.
     */
    public ClienteRepository clienteRepository;

    /**
     * Construtor padrão que inicializa o repositório de clientes.
     */

    public ClienteResource(){
        clienteRepository = new ClienteRepository();
    }

    /**
     * Obtém todos os clientes.
     *
     * @return Lista de clientes no formato JSON.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cliente> readAll() {
        return clienteRepository.reedAll();
    }

    /**
     * Obtém um cliente pelo seu ID.
     *
     * @param id O ID do cliente.
     * @return Resposta com o cliente no formato JSON se encontrado, caso contrário, retorna NOT_FOUND.
     */
    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") int id) {
        var cliente = clienteRepository.findById(id);
        return cliente.isPresent() ?
                Response.ok(cliente.get()).build() :
                Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * Obtém um cliente pelo seu CPF.
     *
     * @param cpf O CPF do cliente.
     * @return Resposta com o cliente no formato JSON se encontrado, caso contrário, retorna NOT_FOUND.
     */
    @GET
    @Path("/cpf/{cpf}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("cpf") String cpf) {
        var cliente = clienteRepository.findByCpf(cpf.replaceAll("[^0-9]", ""));
        return cliente.isPresent() ?
                Response.ok(cliente.get()).build() :
                Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * Cria um novo cliente.
     *
     * @param cliente O cliente a ser criado no formato JSON.
     * @return Resposta indicando sucesso (CREATED) ou erro (BAD_REQUEST) com mensagem.
     */
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

    /**
     * Atualiza um cliente pelo seu ID.
     *
     * @param id      O ID do cliente a ser atualizado.
     * @param cliente O cliente com os novos dados no formato JSON.
     * @return Resposta indicando sucesso (NO_CONTENT) ou erro (BAD_REQUEST) com mensagem.
     */
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

    /**
     * Atualiza o ID da empresa associada a um cliente.
     *
     * @param empresa_id O ID da empresa associada ao cliente.
     * @param cliente    O cliente com o novo ID da empresa no formato JSON.
     * @return Resposta indicando sucesso (NO_CONTENT) ou erro (BAD_REQUEST) com mensagem.
     */
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

    /**
     * Exclui um cliente pelo seu CPF.
     *
     * @param cpf O CPF do cliente a ser excluído.
     * @return Resposta indicando sucesso (NO_CONTENT) ou erro (BAD_REQUEST) com mensagem.
     */
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

    /**
     * Exclui um cliente pelo seu ID.
     *
     * @param id O ID do cliente a ser excluído.
     * @return Resposta indicando sucesso (NO_CONTENT) ou erro (BAD_REQUEST) com mensagem.
     */
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

    /**
     * Obtém o ID de um cliente pelo seu CPF.
     *
     * @param cpf O CPF do cliente.
     * @return Resposta com o ID do cliente no formato JSON se encontrado, caso contrário, retorna NOT_FOUND.
     */
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
