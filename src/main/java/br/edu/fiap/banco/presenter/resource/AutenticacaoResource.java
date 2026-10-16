package br.edu.fiap.banco.presenter.resource;

import br.edu.fiap.banco.presenter.dto.AutenticacaoRequest;
import br.edu.fiap.banco.presenter.dto.UsuarioAutenticadoResponse;
import br.edu.fiap.banco.application.usecase.AutenticacaoService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.json.JsonNumber;
import jakarta.json.JsonString;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/api/auth")
@Consumes(MediaType.APPLICATION_JSON)
public class AutenticacaoResource {
    @Inject
    JsonWebToken jwt;

    private final AutenticacaoService service;

    public AutenticacaoResource(AutenticacaoService service) {
        this.service = service;
    }

    @POST
    @Path("/login")
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(AutenticacaoRequest request) {
        return service.autenticaEGeraToken(request)
                .map(token -> Response.ok(token).build())
                .orElseGet(() -> Response.status(Response.Status.UNAUTHORIZED)
                        .entity("""
                        {
                          "mensagem": "Credenciais inválidas.",
                          "detalhes": "Usuário e/ou senha incorretos."
                        }
                        """).build());
    }

    @GET
    @Path("/me")
    @RolesAllowed(AutenticacaoService.ROLE)
    public UsuarioAutenticadoResponse me() {
        return new UsuarioAutenticadoResponse(
                jwt.getSubject(),
                lerTexto("nome"),
                lerId("usuarioId"),
                jwt.getGroups()
        );
    }

    private String lerTexto(String claim) {
        Object valor = jwt.getClaim(claim);
        if(valor instanceof JsonString json) {
            return json.getString();
        }
        return valor == null ? null : valor.toString();
    }

    private Long lerId(String claim) {
        Object valor = jwt.getClaim(claim);
        if(valor instanceof JsonNumber json) {
            return json.longValue();
        }
        if(valor instanceof Number numero) {
            return numero.longValue();
        }
        return valor == null ? null : Long.valueOf(valor.toString());
    }
}
