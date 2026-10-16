package br.edu.fiap.banco.application.usecase;

import br.edu.fiap.banco.presenter.dto.AutenticacaoRequest;

import java.util.Optional;

public interface AutenticacaoUseCase {

    Optional<String> autenticaEGeraToken(AutenticacaoRequest request);
}
