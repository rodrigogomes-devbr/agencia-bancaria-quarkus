package br.edu.fiap.banco.application.exception;

import br.edu.fiap.banco.domain.exception.ErroNegocioException;
import jakarta.ws.rs.core.Response;

public class TipoContaNaoEncontradoException
        extends ErroNegocioException {

    public TipoContaNaoEncontradoException(Long id) {
        super(Response.Status.NOT_FOUND, "Tipo de conta não encontrado para o ID: " + id);
    }
}