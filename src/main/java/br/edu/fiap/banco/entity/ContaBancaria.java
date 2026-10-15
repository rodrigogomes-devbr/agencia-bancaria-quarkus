package br.edu.fiap.banco.entity;

import java.math.BigDecimal;

public record ContaBancaria(
        Long id,
        String agencia,
        String numero,
        BigDecimal saldo,
        boolean ativa,
        Pessoa titular,
        TipoConta tipoConta
) {

    public ContaBancaria {
        if(agencia == null || agencia.isBlank() || agencia.matches("\\s{4}")){
            throw new IllegalArgumentException("Agencia deve ter 4 numeros");
        }

        if(numero == null || numero.isBlank() || numero.matches("\\s{6}-\\s")){
            throw new IllegalArgumentException("Numero da conta invalido");
        }

        if(saldo == null || saldo.signum() < 0){
            throw new IllegalArgumentException("Saldo não pode ser negativo");
        }

        if(titular == null || tipoConta == null){
            throw new IllegalArgumentException("Titular e/ou tipo conta são obrigatórios");
        }
    }

    public static ContaBancaria nova(
            String agencia,
            String numero,
            Pessoa titular,
            TipoConta tipoConta) {
        return new ContaBancaria(
                null,
                agencia,
                numero,
                BigDecimal.ZERO,
                true,
                titular,
                tipoConta
        );
    }

    public ContaBancaria comId(Long novoId) {
        return new ContaBancaria(
                novoId,
                agencia,
                numero,
                saldo,
                ativa,
                titular,
                tipoConta
        );
    }

    public ContaBancaria atualizar(
            String novaAgencia,
            String novoNumero,
            TipoConta novoTipo
    ) {
        return new ContaBancaria(
                id,
                novaAgencia,
                novoNumero,
                saldo,
                ativa,
                titular,
                novoTipo
        );
    }

    public ContaBancaria encerrar() {
        return new ContaBancaria(
                id,
                agencia,
                numero,
                saldo,
                false,
                titular,
                tipoConta
        );
    }
}
