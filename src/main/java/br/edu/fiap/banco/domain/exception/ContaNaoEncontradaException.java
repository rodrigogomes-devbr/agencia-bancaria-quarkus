package br.edu.fiap.banco.domain.exception;

import jakarta.ws.rs.core.Response;

public class ContaNaoEncontradaException extends ErroNegocioException {
    public ContaNaoEncontradaException() {
        super(Response.Status.NOT_FOUND, "Conta bancária não encontrada");
    }
}
