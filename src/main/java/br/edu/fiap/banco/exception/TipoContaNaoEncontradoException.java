package br.edu.fiap.banco.exception;

public class TipoContaNaoEncontradoException
        extends RuntimeException {

    public TipoContaNaoEncontradoException(Long id) {
        super("Tipo de conta não encontrado para o ID: " + id);
    }
}