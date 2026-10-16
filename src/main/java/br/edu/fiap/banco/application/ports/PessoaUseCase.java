package br.edu.fiap.banco.application.ports;

import br.edu.fiap.banco.presenter.dto.PessoaRequest;
import br.edu.fiap.banco.presenter.dto.PessoaResponse;

public interface PessoaUseCase {

    PessoaResponse cadastrar(PessoaRequest request);
    PessoaResponse getPessoaById(Long id);
    PessoaResponse atualizar(Long id, PessoaRequest request);
    void excluir(Long id);
}
