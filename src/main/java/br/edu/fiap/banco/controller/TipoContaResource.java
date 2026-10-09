package br.edu.fiap.banco.controller;

import br.edu.fiap.banco.dto.PessoaRequest;
import br.edu.fiap.banco.dto.PessoaResponse;
import br.edu.fiap.banco.dto.TipoContaRequest;
import br.edu.fiap.banco.dto.TipoContaResponse;
import br.edu.fiap.banco.service.PessoaService;
import br.edu.fiap.banco.service.TipoContaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;

@Path("/api/tipos-conta")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Tipo Conta")
public class TipoContaResource {

    private final TipoContaService service;
    @Inject
    public TipoContaResource(TipoContaService service) {
        this.service = service;
    }

    @POST
    @Operation(summary = "Cadastra um Tipo de Conta")
    public Response cadastrar(@NotNull @Valid TipoContaRequest request) {
        TipoContaResponse pessoa = service.cadastrar(request);
        return Response.created(URI.create("/api/tipo-conta/" + pessoa.id()))
                .entity(pessoa)
                .build();
    }

    @GET
    public TipoContaResponse listarTipoConta() {
        return service.listarTipoConta();
    }


    @GET
    @Path("/{id}")
    public TipoContaResponse getTipoContaById(@PathParam("id") Long id) {
        return service.getTipoContaById(id);
    }

    @PUT
    @Path("/{id}")
    public PessoaResponse atualizar(@PathParam("id") Long id, @NotNull @Valid TipoContaRequest request) {
        return service.atualizar(id, request);
    }

    @DELETE
    @Path("/{id}")
    public Response excluir(@PathParam("id") Long id) {
        service.excluir(id);
        return Response.noContent().build();
    }

}
