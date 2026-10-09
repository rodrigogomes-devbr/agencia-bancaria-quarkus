package br.edu.fiap.banco.exception;

public class TipoContaJaCadastradoException
        extends RuntimeException {

    public TipoContaJaCadastradoException(String nome) {
        super("Já existe um tipo de conta cadastrado com o nome: " + nome);
    }
}