package br.edu.fiap.banco.application.usecase;

import br.edu.fiap.banco.presenter.dto.AutenticacaoRequest;
import br.edu.fiap.banco.domain.model.Usuario;
import br.edu.fiap.banco.infrastructure.repository.UsuarioRepositoryJpa;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotAuthorizedException;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class AutenticacaoUseCaseImpl implements AutenticacaoUseCase {
    public static final String ROLE = "usuario";

    private final UsuarioRepositoryJpa usuarioRepositoryJpa;
    private final String segredo;
    private final String emissor;

    public AutenticacaoUseCaseImpl(
            UsuarioRepositoryJpa usuarioRepositoryJpa,
            @ConfigProperty(name = "security.jwt.secret") String segredo,
            @ConfigProperty(name = "security.jwt.issuer") String emissor) {
        this.usuarioRepositoryJpa = usuarioRepositoryJpa;
        this.segredo = segredo;
        this.emissor = emissor;
    }

    @Override
    public Optional<String> autenticaEGeraToken(AutenticacaoRequest request) {
        Usuario usuario = usuarioRepositoryJpa.buscaPorEmail(request.email())
                .orElseThrow(() -> new NotAuthorizedException("Bearer"));

        if(!usuario.isAtivo() || !BcryptUtil.matches(request.senha(), usuario.getSenha())){
            throw new NotAuthorizedException("Bearer");
        }

        long agora = Instant.now().getEpochSecond();
        long expiracao = agora + 900;

        return usuarioRepositoryJpa.buscaPorEmail(request.email())
                .filter(user -> BcryptUtil.matches(request.senha(), user.getSenha()))
                .map(user -> Jwt.issuer(emissor)
                        .subject(usuario.getEmail())
                        .expiresIn(Duration.ofSeconds(900))
                        .expiresAt(expiracao)
                        .claim("usuarioId", usuario.getId())
                        .claim("nome", usuario.getNome())
                        .groups(Set.of(ROLE, usuario.getEmail()))
                        .signWithSecret(segredo));
    }
}
