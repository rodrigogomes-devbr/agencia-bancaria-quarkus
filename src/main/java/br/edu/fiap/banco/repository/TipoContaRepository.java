package br.edu.fiap.banco.repository;

import br.edu.fiap.banco.entity.TipoConta;

import java.util.List;
import java.util.Optional;

public interface TipoContaRepository {

    TipoConta salvar(TipoConta tipoConta);
    List<TipoConta> listarTodos();
    Optional<TipoConta> buscarPorId(Long id);
    TipoConta atualizar(TipoConta tipoConta);
    boolean excluir(Long id);

}