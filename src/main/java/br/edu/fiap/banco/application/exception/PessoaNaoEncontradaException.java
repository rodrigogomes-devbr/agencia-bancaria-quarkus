package br.edu.fiap.banco.application.exception;

import br.edu.fiap.banco.domain.exception.ErroNegocioException;
import jakarta.ws.rs.core.Response;

/** Pessoa ausente na consulta por id. O tratamento global responde HTTP 404. */
public class PessoaNaoEncontradaException extends ErroNegocioException {
    public PessoaNaoEncontradaException() {
        super(Response.Status.NOT_FOUND, "Pessoa não encontrada.");
    }
}
