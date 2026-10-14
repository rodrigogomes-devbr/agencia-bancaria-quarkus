package br.edu.fiap.banco.repository;

import br.edu.fiap.banco.entity.ContaBancaria;

import java.util.List;
import java.util.Optional;

public interface ContaBancariaRepository {

    boolean existePorAgenciaENumero(String agencia, String numero);

    //SALVAR (TURMA)

    //BUSCA POR ID(TURMA)
    Optional<ContaBancaria> buscarPorId(Long id);

    List<ContaBancaria> listar();

    List<ContaBancaria> listarPorTitular(Long titularId);

    ContaBancaria atualizar(ContaBancaria conta);

    boolean encerrar(Long id);
}
