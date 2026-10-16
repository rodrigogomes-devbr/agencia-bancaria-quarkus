package br.edu.fiap.banco.presenter.resource;

import br.edu.fiap.banco.presenter.dto.TipoContaRequest;
import br.edu.fiap.banco.presenter.dto.TipoContaResponse;
import br.edu.fiap.banco.application.usecase.TipoContaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;
import java.util.List;

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
        TipoContaResponse tipoConta = service.cadastrar(request);
        return Response.created(URI.create("/api/tipo-conta/" + tipoConta.id()))
                .entity(tipoConta)
                .build();
    }

    @GET
    @Operation(summary = "Lista todos os tipos de conta")
    public List<TipoContaResponse> listarTipoConta() {
        return service.listarTipoConta();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Lista tipo de conta por id")
    public TipoContaResponse getTipoContaById(@PathParam("id") Long id) {
        return service.getTipoContaById(id);
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualiza o tipo de conta")
    public TipoContaResponse atualizar(@PathParam("id") Long id, @NotNull @Valid TipoContaRequest request) {
        return service.atualizar(id, request);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Exclui um tipo de conta")
    public Response excluir(@PathParam("id") Long id) {
        service.excluir(id);
        return Response.noContent().build();
    }

}
