package com.nadson.bankapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransferenciaDTO(
        @NotNull(message = "O ID da conta de origem é obrigatório")
        Long contaOrigemId,

        @NotNull(message = "O ID da conta de destine é obrigatório")
        Long contaDestinoId,

        @NotNull(message = "O valor é obrigatório")
        @Positive(message = "O valor deve ser positivo")
        BigDecimal valor,
        String descricao
) {
}
