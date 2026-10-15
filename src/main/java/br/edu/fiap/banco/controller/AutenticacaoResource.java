package br.edu.fiap.banco.controller;

import br.edu.fiap.banco.dto.AutenticacaoRequest;
import br.edu.fiap.banco.service.AutenticacaoService;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/auth")
@Consumes(MediaType.APPLICATION_JSON)
@PermitAll
public class AutenticacaoResource {
    private final AutenticacaoService service;

    public AutenticacaoResource(AutenticacaoService service) {
        this.service = service;
    }

    @POST
    @Path("/login")
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(AutenticacaoRequest request) {
        return service.autenticaEGeraToken(request.email(), request.senha())
                .map(token -> Response.ok(token).build())
                .orElseGet(() -> Response.status(Response.Status.UNAUTHORIZED)
                        .entity("""
                        {
                          "mensagem": "Credenciais inválidas.",
                          "detalhes": "Usuário e/ou senha incorretos."
                        }
                        """).build());
    }
}
