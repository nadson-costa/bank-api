package com.nadson.bankapi.controller;

import com.nadson.bankapi.dto.ContaDTO;
import com.nadson.bankapi.dto.CreateContaDTO;
import com.nadson.bankapi.dto.TransacaoDTO;
import com.nadson.bankapi.model.Conta;
import com.nadson.bankapi.model.Transacao;
import com.nadson.bankapi.service.TransacaoService;
import com.nadson.bankapi.service.ContaService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/contas")

public class ContaController {
    private final ContaService contaService;
    private final TransacaoService transacaoService;

    public ContaController(ContaService contaService, TransacaoService transacaoService) {
        this.contaService = contaService;
        this.transacaoService = transacaoService;
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

    @PostMapping
    public ResponseEntity<ContaDTO> criarConta(@Valid @RequestBody CreateContaDTO dto){
        return contaService.criarConta(dto.usuarioId())
                .map(this::toDTO)
                .map(conta -> ResponseEntity.status(HttpStatus.CREATED).body(conta))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/extrato")
    public ResponseEntity<List<TransacaoDTO>> consultarExtrato(@PathVariable Long id){
        if (contaService.buscarPorId(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }

        List<TransacaoDTO> extrato = transacaoService.buscarExtrato(id)
                .stream()
                .map(this::toTransacaoDTO)
                .toList();
        return ResponseEntity.ok(extrato);
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

    private TransacaoDTO toTransacaoDTO(Transacao transacao){
        return new TransacaoDTO(
                transacao.getId(),
                transacao.getTipo(),
                transacao.getValor(),
                transacao.getContaOrigem() != null ? transacao.getContaOrigem().getId() : null,
                transacao.getContaDestino() != null ? transacao.getContaDestino().getId() : null,
                transacao.getDescricao(),
                transacao.getCreatedAt()

        );
    }

}
