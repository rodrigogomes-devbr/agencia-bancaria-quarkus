package br.edu.fiap.banco.domain.model;

public record TipoConta(Long id, String nome) {

    public TipoConta{
        if(nome == null || nome.isBlank()){
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        if(nome.length() > 41 ) {
            throw new IllegalArgumentException("Nome deve ter no máximo 40 caracteres");
        }

        nome = nome.trim().toUpperCase();
    }

    public TipoConta atualizar(String novoNome){
        return new TipoConta(id, novoNome);
    }


    // Devolve a mesma pessoa ja com a chave gerada (id)
    public TipoConta comId(Long novoId) {
        return new TipoConta(novoId, nome);
    }
}
