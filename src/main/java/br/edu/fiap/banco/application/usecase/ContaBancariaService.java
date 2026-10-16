package br.edu.fiap.banco.application.usecase;

import br.edu.fiap.banco.presenter.dto.ContaBancariaRequest;
import br.edu.fiap.banco.presenter.dto.ContaBancariaResponse;

import br.edu.fiap.banco.domain.model.ContaBancaria;
import br.edu.fiap.banco.domain.model.Pessoa;
import br.edu.fiap.banco.domain.model.TipoConta;
import br.edu.fiap.banco.domain.exception.ContaJaCadastradaException;
import br.edu.fiap.banco.domain.exception.ContaNaoEncontradaException;
import br.edu.fiap.banco.domain.exception.PessoaNaoEncontradaException;
import br.edu.fiap.banco.domain.exception.TipoContaNaoEncontradoException;
import br.edu.fiap.banco.application.ports.ContaBancariaRepository;
import br.edu.fiap.banco.application.ports.PessoaRepository;
import br.edu.fiap.banco.application.ports.TipoContaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;


@ApplicationScoped
public class ContaBancariaService {
    private final ContaBancariaRepository contas;
    private final PessoaRepository pessoas;
    private final TipoContaRepository tipos;


    @Inject
    public ContaBancariaService(ContaBancariaRepository contas, PessoaRepository pessoas, TipoContaRepository tipos) {
        this.contas = contas;
        this.pessoas = pessoas;
        this.tipos = tipos;
    }

    /** Lista entidades e as converte para DTOs sem expor a camada de domínio. */
    public List<ContaBancariaResponse> listar() {
        return contas.listar().stream().map(ContaBancariaResponse::de).toList();
    }

    /**
     * Lista as contas de um titular.
     * A consulta prévia diferencia pessoa inexistente de pessoa sem contas.
     */
    public List<ContaBancariaResponse> listarPorTitular(Long titularId) {
        if (pessoas.getPessoaById(titularId).isEmpty()) {
            throw new PessoaNaoEncontradaException();
        }
        return contas.listarPorTitular(titularId).stream().map(ContaBancariaResponse::de).toList();
    }

    public ContaBancariaResponse buscarPorId(Long id) {
        return ContaBancariaResponse.de(contas.buscarPorId(id).orElseThrow(ContaNaoEncontradaException::new));
    }

    public ContaBancariaResponse criarConta(ContaBancariaRequest request) {
        if(contas.existePorAgenciaENumero(request.agencia(), request.numero())) {
            throw new ContaJaCadastradaException();
        }

        Pessoa titular = pessoas.getPessoaById(request.titularId())
                .orElseThrow(PessoaNaoEncontradaException::new);

        TipoConta tipoConta = tipos.buscarPorId(request.tipoContaId())
                .orElseThrow(() -> new TipoContaNaoEncontradoException(request.tipoContaId()));

        ContaBancaria novaConta = ContaBancaria.nova(
                request.agencia(),
                request.numero(),
                titular,
                tipoConta
        );

        return ContaBancariaResponse.de(contas.salvar(novaConta));
    }


    /** Encerra logicamente a conta e transforma ID ausente em 404. */
    public void encerrar(Long id) {
        if (!contas.encerrar(id)) {
            throw new ContaNaoEncontradaException();
        }
    }

    /**
     * Atualiza agência, número e tipo, preservando titular, saldo e estado.
     */
    public ContaBancariaResponse atualizar(Long id, ContaBancariaRequest request) {
        ContaBancaria atual = contas.buscarPorId(id)
                .orElseThrow(ContaNaoEncontradaException::new);
        TipoConta tipo = tipos.buscarPorId(request.tipoContaId())
                .orElseThrow(() -> new TipoContaNaoEncontradoException(id));
        return ContaBancariaResponse.de(contas.atualizar(atual.atualizar(request.agencia(), request.numero(), tipo)));
    }
}
