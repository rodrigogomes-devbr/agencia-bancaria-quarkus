package br.edu.fiap.banco.application.usecase;

import br.edu.fiap.banco.presenter.dto.TipoContaRequest;
import br.edu.fiap.banco.presenter.dto.TipoContaResponse;

import java.util.List;

public interface TipoContaUseCase {

    TipoContaResponse cadastrar(TipoContaRequest request);
    List<TipoContaResponse> listarTipoConta();
    TipoContaResponse getTipoContaById(Long id);
    TipoContaResponse atualizar(Long id, TipoContaRequest request);
    void excluir(Long id);
}
