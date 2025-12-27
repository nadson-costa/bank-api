package com.nadson.bankapi.dto;

public record AuthResponseDTO(
        String token,
        String email,
        String nome
) {
}
