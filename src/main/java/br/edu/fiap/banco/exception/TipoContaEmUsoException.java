package br.edu.fiap.banco.exception;

public class TipoContaEmUsoException
        extends RuntimeException {

    public TipoContaEmUsoException(Long id) {
        super(
                "O tipo de conta de ID " + id +
                        " não pode ser excluído porque está associado a uma conta bancária."
        );
    }
}