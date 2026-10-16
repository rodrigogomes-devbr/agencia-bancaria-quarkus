package br.edu.fiap.banco.domain.exception;

import jakarta.ws.rs.core.Response;

public class TipoContaEmUsoException
        extends ErroNegocioException {

    public TipoContaEmUsoException(Long id) {
        super(Response.Status.CONFLICT,
                "O tipo de conta de ID " + id +
                        " não pode ser excluído porque está associado a uma conta bancária."
        );
    }
}