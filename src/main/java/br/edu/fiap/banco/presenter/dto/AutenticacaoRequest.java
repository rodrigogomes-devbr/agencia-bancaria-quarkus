package br.edu.fiap.banco.presenter.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Credenciais usadas para solicitar o JWT")
public record AutenticacaoRequest(
        @Schema(example = "ana@example.test")
        @NotBlank(message = "E-mail é obrigatório.")
        @Email(message = "E-mail deve possuir formato válido.")
        String email,

        @Schema(example = "Senha@123")
        @NotBlank(message = "Senha é obrigatória.")
        String senha) {
}
