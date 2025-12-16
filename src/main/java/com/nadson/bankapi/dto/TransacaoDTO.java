package com.nadson.bankapi.dto;

import com.nadson.bankapi.model.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransacaoDTO(
        Long id,
        TipoTransacao tipo,
        BigDecimal valor,
        Long contaOrigemId,
        Long contaDestinoId,
        String descricao,
        LocalDateTime createdAt
) {
}
