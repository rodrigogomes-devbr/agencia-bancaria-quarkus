package br.edu.fiap.banco.dto;

import br.edu.fiap.banco.entity.Pessoa;
import br.edu.fiap.banco.entity.TipoConta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "ContaBancariaRequest", description = "Dados para abrir ou atualizar uma conta")
public record ContaBancariaRequest(
        @NotBlank @Pattern(regexp = "\\d{4}", message = "Agência deve ter quatro números.")
        @Schema(example = "0001") String agencia,
        @NotBlank @Pattern(regexp = "\\d{6}-\\d", message = "Número deve seguir 000000-0.")
        @Schema(example = "100001-1") String numero,
        @NotNull @Schema(example = "1") Long titularId,
        @NotNull @Schema(example = "1") Long tipoContaId) {
}
        
