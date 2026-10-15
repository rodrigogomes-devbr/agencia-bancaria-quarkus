package br.edu.fiap.banco.exception;

import jakarta.ws.rs.core.Response;

public class EmailJaCadastradoException extends ErroNegocioException {
    public EmailJaCadastradoException() {
        super(Response.Status.CONFLICT, "E-mail já cadastrad");
    }
}
