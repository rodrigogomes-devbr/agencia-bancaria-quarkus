package br.edu.fiap.banco.application.usecase;

import br.edu.fiap.banco.presenter.dto.TipoContaRequest;
import br.edu.fiap.banco.presenter.dto.TipoContaResponse;
import br.edu.fiap.banco.domain.model.TipoConta;
import br.edu.fiap.banco.domain.exception.TipoContaJaCadastradoException;
import br.edu.fiap.banco.application.exception.TipoContaNaoEncontradoException;
import br.edu.fiap.banco.infrastructure.repository.TipoContaRepositoryImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class TipoContaUseCaseImpl implements TipoContaUseCase {

    private final TipoContaRepositoryImpl repository;

    @Inject
    public TipoContaUseCaseImpl(TipoContaRepositoryImpl repository) {
        this.repository = repository;
    }

    @Override
    public TipoContaResponse cadastrar(TipoContaRequest request) {
        if(repository.existePorTipo(request.nome())) {
            throw new TipoContaJaCadastradoException(request.nome());
        }

        TipoConta tipoConta = new TipoConta(null, request.nome());
        return TipoContaResponse.de(repository.salvar(tipoConta));
    }

    @Override
    public List<TipoContaResponse> listarTipoConta() {
        return repository.listarTodos().stream().map(TipoContaResponse::de).toList();
    }

    @Override
    public TipoContaResponse getTipoContaById(Long id) {
        return repository.buscarPorId(id)
                .map(TipoContaResponse::de)
                .orElseThrow(() -> new TipoContaNaoEncontradoException(id));
    }

    @Override
    public TipoContaResponse atualizar(Long id, TipoContaRequest request) {
        TipoConta tipoContaAtual = repository.buscarPorId(id).orElseThrow(() -> new TipoContaNaoEncontradoException(id));

        if(tipoContaAtual.nome().equalsIgnoreCase(request.nome())) {
            throw new TipoContaJaCadastradoException(request.nome());
        }

        TipoConta tipoContaNovo = tipoContaAtual.atualizar(request.nome());

        return TipoContaResponse.de(repository.atualizar(tipoContaNovo));
    }

    @Override
    public void excluir(Long id) {
        if(!repository.excluir(id)) {
            throw new TipoContaNaoEncontradoException(id);
        }

        repository.excluir(id);
    }
}
