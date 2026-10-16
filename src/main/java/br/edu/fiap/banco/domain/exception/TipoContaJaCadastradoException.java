package br.edu.fiap.banco.domain.exception;

import jakarta.ws.rs.core.Response;

public class TipoContaJaCadastradoException extends ErroNegocioException {

    public TipoContaJaCadastradoException(String nome) {
        super(Response.Status.CONFLICT, "Tipo de conta já cadastrado: " + nome);
    }
}