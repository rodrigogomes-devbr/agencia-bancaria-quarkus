package br.edu.fiap.banco.entity;

public record TipoConta(Long id, String nome) {
    public TipoConta{
        if(nome == null || nome.isBlank()){
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        nome = nome.trim();
    }

}
