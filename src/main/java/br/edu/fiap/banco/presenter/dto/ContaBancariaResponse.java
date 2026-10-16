package br.edu.fiap.banco.presenter.dto;

import br.edu.fiap.banco.domain.model.ContaBancaria;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(name = "ContaBancariaResponse", description = "Conta com titular e tipo relacionados")
public record ContaBancariaResponse(
        Long id,
        String agencia,
        String numero,
        BigDecimal saldo,
        boolean ativa,
        Long titularId,
        String titularNome,
        Long tipoContaId,
        String tipoContaNome) {
    public static ContaBancariaResponse de(ContaBancaria conta) {
        return new ContaBancariaResponse(conta.id(), conta.agencia(), conta.numero(), conta.saldo(), conta.ativa(),
                conta.titular().id(), conta.titular().nome(), conta.tipoConta().id(), conta.tipoConta().nome());
    }
}
