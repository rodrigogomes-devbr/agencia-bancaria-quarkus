package br.edu.fiap.banco.service;

import br.edu.fiap.banco.dto.PessoaResponse;
import br.edu.fiap.banco.dto.TipoContaRequest;
import br.edu.fiap.banco.dto.TipoContaResponse;
import br.edu.fiap.banco.entity.TipoConta;
import br.edu.fiap.banco.exception.PessoaNaoEncontradaException;
import br.edu.fiap.banco.exception.TipoContaJaCadastradoException;
import br.edu.fiap.banco.exception.TipoContaNaoEncontradoException;
import br.edu.fiap.banco.repository.TipoContaRepositoryImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@ApplicationScoped
public class TipoContaService {

    private final TipoContaRepositoryImpl repository;

    @Inject
    public TipoContaService(TipoContaRepositoryImpl repository) {
        this.repository = repository;
    }

    public TipoContaResponse cadastrar(TipoContaRequest request) {
        if(repository.existePorTipo(request.nome())) {
            throw new TipoContaJaCadastradoException(request.nome());
        }

        TipoConta tipoConta = new TipoConta(null, request.nome());
        return TipoContaResponse.de(repository.salvar(tipoConta));
    }

    public List<TipoContaResponse> listarTipoConta() {
        return repository.listarTodos().stream().map(TipoContaResponse::de).toList();
    }

    public TipoContaResponse getTipoContaById(Long id) {
        return repository.buscarPorId(id)
                .map(TipoContaResponse::de)
                .orElseThrow(() -> new TipoContaNaoEncontradoException(id));
    }

    public TipoContaResponse atualizar(Long id, TipoContaRequest request) {
        TipoConta tipoContaAtual = repository.buscarPorId(id).orElseThrow(() -> new TipoContaNaoEncontradoException(id));

        if(tipoContaAtual.nome().equalsIgnoreCase(request.nome())) {
            throw new TipoContaJaCadastradoException(request.nome());
        }

        TipoConta tipoContaNovo = tipoContaAtual.atualizar(request.nome());

        return TipoContaResponse.de(repository.atualizar(tipoContaNovo));
    }

    public void excluir(Long id) {
        if(!repository.excluir(id)) {
            throw new TipoContaNaoEncontradoException(id);
        }

        repository.excluir(id);
    }
}
