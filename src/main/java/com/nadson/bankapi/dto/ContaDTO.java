package com.nadson.bankapi.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ContaDTO(
        Long id,
        String numeroConta,
        BigDecimal saldo,
        Boolean ativa,
        LocalDateTime createdAt
) {
}
