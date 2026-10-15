package br.edu.fiap.banco.controller;

import br.edu.fiap.banco.dto.ContaBancariaRequest;
import br.edu.fiap.banco.dto.ContaBancariaResponse;
import br.edu.fiap.banco.service.ContaBancariaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.List;


@Path("/api/contas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ContaBancariaResource {
    private final ContaBancariaService service;

    /** Injeção por construtor mantém a dependência explícita e testável. */
    @Inject
    public ContaBancariaResource(ContaBancariaService service) {
        this.service = service;
    }

    @POST
    public Response abrir(@NotNull @Valid ContaBancariaRequest request, @Context UriInfo uriInfo) {
        ContaBancariaResponse resposta = service.criarConta(request);
        URI location = uriInfo.getAbsolutePathBuilder().path(resposta.id().toString()).build();
        return Response.created(location).entity(resposta).build();
    }

    /** Atende {@code GET /api/contas} e devolve a coleção completa. */
    @GET
    public List<ContaBancariaResponse> listar() {
        return service.listar();
    }

    /** Atende {@code GET /api/contas/pessoa/{pessoaId}} e demonstra o relacionamento 1:N. */
    @GET
    @Path("/pessoa/{pessoaId}")
    public List<ContaBancariaResponse> listarPorPessoa(@PathParam("pessoaId") Long id) {
        return service.listarPorTitular(id);
    }

    @GET
    @Path("/{id}")
    public ContaBancariaResponse listarPorId(@PathParam("id") Long id) {
        return service.buscarPorId(id);
    }

    /** Atende {@code PUT /api/contas/{id}} e substitui os campos editáveis. */
    @PUT
    @Path("/{id}")
    public ContaBancariaResponse atualizar(
            @PathParam("id") Long id,
            @NotNull @Valid ContaBancariaRequest request) {
        return service.atualizar(id, request);
    }

    /** Atende {@code DELETE /api/contas/{id}} com encerramento lógico e HTTP 204. */
    @DELETE
    @Path("/{id}")
    public Response encerrar(@PathParam("id") Long id) {
        service.encerrar(id);
        return Response.noContent().build();
    }
}
