package com.nadson.bankapi.dto;

import com.nadson.bankapi.model.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransacaoDTO(
        Long id,
        TipoTransacao tipo,
        BigDecimal valor,
        Long contaOrigemId,
        String nomeOrigem,
        Long contaDestinoId,
        String nomeDestino,
        String descricao,
        LocalDateTime createdAt
) {
}
