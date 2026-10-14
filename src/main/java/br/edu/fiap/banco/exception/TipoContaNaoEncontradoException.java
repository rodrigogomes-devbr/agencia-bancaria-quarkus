package br.edu.fiap.banco.exception;

import jakarta.ws.rs.core.Response;

public class TipoContaNaoEncontradoException
        extends ErroNegocioException {

    public TipoContaNaoEncontradoException(Long id) {
        super(Response.Status.NOT_FOUND, "Tipo de conta não encontrado para o ID: " + id);
    }
}