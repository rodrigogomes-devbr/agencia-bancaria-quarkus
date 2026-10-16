package br.edu.fiap.banco.application.usecase;

import br.edu.fiap.banco.presenter.dto.ContaBancariaRequest;
import br.edu.fiap.banco.presenter.dto.ContaBancariaResponse;

import java.util.List;

public interface ContaBancariaUseCase {

    List<ContaBancariaResponse> listar();
    List<ContaBancariaResponse> listarPorTitular(Long titularId);
    ContaBancariaResponse buscarPorId(Long id);
    ContaBancariaResponse criarConta(ContaBancariaRequest request);
    void encerrar(Long id);
    ContaBancariaResponse atualizar(Long id, ContaBancariaRequest request);
}
