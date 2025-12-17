package com.nadson.bankapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record DepositoDTO(
        @NotNull(message = "O ID da conta é obrigatório")
        Long contaId,

        @NotNull(message = "O valor é obrigatório")
        @Positive(message = "O valor deve ser positivo")
        BigDecimal valor,
        String descricao
) {
}
