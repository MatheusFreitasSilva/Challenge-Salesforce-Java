package org.salesforce.infrastructure;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

/**
 * Classe que implementa um filtro para adicionar cabeçalhos CORS (Cross-Origin Resource Sharing) às respostas.
 */
@Provider
public class CorsFilter implements ContainerResponseFilter {

    /**
     * Adiciona cabeçalhos CORS às respostas.
     *
     * @param requestContext  O contexto da requisição.
     * @param responseContext O contexto da resposta.
     * @throws IOException Se ocorrer um erro ao adicionar os cabeçalhos.
     */
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
            throws IOException {
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "Origin, Content-Type, Accept, Authorization");
        responseContext.getHeaders().add("Access-Control-Max-Age", "86400"); // 24 hours
    }
}

