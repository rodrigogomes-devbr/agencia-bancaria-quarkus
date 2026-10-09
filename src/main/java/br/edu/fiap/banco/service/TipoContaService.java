package br.edu.fiap.banco.service;

import br.edu.fiap.banco.dto.PessoaResponse;
import br.edu.fiap.banco.dto.TipoContaRequest;
import br.edu.fiap.banco.dto.TipoContaResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class TipoContaService {
    public TipoContaResponse cadastrar(TipoContaRequest request) {
    }

    public TipoContaResponse listarTipoConta() {
    }

    public TipoContaResponse getTipoContaById(Long id) {
    }

    public PessoaResponse atualizar(Long id, TipoContaRequest request) {
    }

    public void excluir(Long id) {
    }
}
