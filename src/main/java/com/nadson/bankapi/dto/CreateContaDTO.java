package com.nadson.bankapi.dto;

import jakarta.validation.constraints.NotNull;

public record CreateContaDTO(
        @NotNull(message = "O ID do usuárop é obrigatório")
        Long usuarioId
) {
}
