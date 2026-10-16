package br.edu.fiap.banco.service;

import br.edu.fiap.banco.application.usecase.UsuarioService;
import br.edu.fiap.banco.presenter.dto.UsuarioRequest;
import br.edu.fiap.banco.domain.model.Usuario;
import br.edu.fiap.banco.application.ports.UsuarioRepository;
import br.edu.fiap.banco.infrastructure.security.SenhaBCrypt;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioServiceTest {

    @Test
    @DisplayName("Verifica se repository recebe hash em vez da senha orininal")
    void deveReceberHash() {
        String senhaOriginal = "Senha@123";
        var repository = new UsuarioRepositoryFalso();
        var senhas = new SenhaBCrypt(12);
        var service = new UsuarioService(repository, senhas);

        service.cadastrarUsuario(new UsuarioRequest("Ana Lima", "ana@example.test", senhaOriginal));

        assertNotNull(repository.usuarioSalvo);
        assertNotEquals(senhaOriginal, repository.usuarioSalvo.getSenha());
        assertTrue(senhas.confere(senhaOriginal, repository.usuarioSalvo.getSenha()));
    }

    private static class UsuarioRepositoryFalso implements UsuarioRepository {
        private Usuario usuarioSalvo;

        @Override
        public boolean existePorEmail(String email) {
            return false;
        }

        @Override
        public Usuario salvar(Usuario usuario) {
            usuarioSalvo = usuario;
            return usuario;
        }

        @Override
        public Optional<Usuario> buscaPorEmail(String email) {
            return Optional.empty();
        }
    }
}