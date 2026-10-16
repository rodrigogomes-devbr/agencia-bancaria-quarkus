package br.edu.fiap.banco.presenter.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public record TipoContaRequest(
        @Schema(examples = "CORRENTE")
        @NotBlank(message = "Nome é obrigatório.")
        @Size(min = 3, max = 40, message = "Nome deve ter entre 3 e 40 caracteres")
        String nome) {
}
