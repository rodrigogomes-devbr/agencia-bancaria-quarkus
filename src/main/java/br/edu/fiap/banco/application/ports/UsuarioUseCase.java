package br.edu.fiap.banco.application.ports;

import br.edu.fiap.banco.presenter.dto.UsuarioRequest;
import br.edu.fiap.banco.presenter.dto.UsuarioResponse;

public interface UsuarioUseCase {

    UsuarioResponse cadastrarUsuario(UsuarioRequest request);
}
