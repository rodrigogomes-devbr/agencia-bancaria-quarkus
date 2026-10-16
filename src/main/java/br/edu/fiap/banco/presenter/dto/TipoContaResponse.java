package br.edu.fiap.banco.presenter.dto;

import br.edu.fiap.banco.domain.model.TipoConta;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public record TipoContaResponse(
        @Schema(examples = "1")
        Long id,

        @Schema(examples = "CORRENTE")
        String nome) {

    public static TipoContaResponse de(TipoConta tipoConta) {
        return new TipoContaResponse(tipoConta.id(), tipoConta.nome());
    }
}
