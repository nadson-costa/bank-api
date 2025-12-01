package com.nadson.bankapi.controller;

import com.nadson.bankapi.dto.ContaDTO;
import com.nadson.bankapi.dto.CreateContaDTO;
import com.nadson.bankapi.model.Conta;
import com.nadson.bankapi.service.ContaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/contas")

public class ContaController {
    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaDTO> buscarPorId(@PathVariable Long id){
        return contaService.buscarPorId(id)
                .map(this::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/saldo")
    public ResponseEntity<SaldoDTO> consultarSaldo(@PathVariable Long id){
        return contaService.buscarPorId(id)
                .map(conta -> new SaldoDTO(conta.getSaldo()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private ContaDTO toDTO(Conta conta){
        return new ContaDTO(
                conta.getId(),
                conta.getNumeroConta(),
                conta.getSaldo(),
                conta.getAtiva(),
                conta.getCreatedAt()
        );
    }

    private record SaldoDTO(java.math.BigDecimal saldo) {}


}
