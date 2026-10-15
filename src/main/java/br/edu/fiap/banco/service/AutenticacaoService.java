package br.edu.fiap.banco.service;

import br.edu.fiap.banco.repository.UsuarioRepositoryJpa;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;
import java.util.Optional;

@ApplicationScoped
public class AutenticacaoService {
    private final UsuarioRepositoryJpa usuarioRepositoryJpa;

    public AutenticacaoService(UsuarioRepositoryJpa usuarioRepositoryJpa) {
        this.usuarioRepositoryJpa = usuarioRepositoryJpa;
    }

    public Optional<String> autenticaEGeraToken(String email, String password) {
        return usuarioRepositoryJpa.buscaPorEmail(email)
                .filter(user -> BcryptUtil.matches(password, user.getSenha()))
                .map(user -> Jwt.issuer("agencia-bancaria-quarkus")
                        .upn(user.getEmail())
                        .expiresIn(Duration.ofHours(24))
                        .sign());
    }
}
