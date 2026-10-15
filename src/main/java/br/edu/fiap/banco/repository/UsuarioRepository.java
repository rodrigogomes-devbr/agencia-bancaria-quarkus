package br.edu.fiap.banco.repository;

import br.edu.fiap.banco.entity.Usuario;

import java.util.Optional;

public interface UsuarioRepository {

    boolean existePorEmail(String email);

    Usuario salvar(Usuario usuario);

    Optional<Usuario> buscaPorEmail(String email);
}
