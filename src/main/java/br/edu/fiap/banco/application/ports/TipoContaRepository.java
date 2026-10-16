package br.edu.fiap.banco.application.ports;

import br.edu.fiap.banco.domain.model.TipoConta;

import java.util.List;
import java.util.Optional;

public interface TipoContaRepository {

    boolean existePorTipo(String nome);
    TipoConta salvar(TipoConta tipoConta);
    List<TipoConta> listarTodos();
    Optional<TipoConta> buscarPorId(Long id);
    TipoConta atualizar(TipoConta tipoConta);
    boolean excluir(Long id);

}