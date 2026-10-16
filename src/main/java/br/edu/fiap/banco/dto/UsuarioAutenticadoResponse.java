package br.edu.fiap.banco.dto;

import java.util.Set;

public record UsuarioAutenticadoResponse(
        String email,
        String nome,
        Long usuarioId,
        Set<String> roles
) {
}
