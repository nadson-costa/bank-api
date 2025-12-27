package com.nadson.bankapi.controller;

import com.nadson.bankapi.dto.DepositoDTO;
import com.nadson.bankapi.dto.SaqueDTO;
import com.nadson.bankapi.dto.TransacaoDTO;
import com.nadson.bankapi.dto.TransferenciaDTO;
import com.nadson.bankapi.model.Transacao;
import com.nadson.bankapi.service.TransacaoService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping("/deposito")
    public ResponseEntity<TransacaoDTO> depositar(@Valid @RequestBody DepositoDTO dto) {
        return transacaoService.depositar(dto.contaId(), dto.valor(), dto.descricao())
                .map(this::toDTO)
                .map(transacao -> ResponseEntity.status(HttpStatus.CREATED).body(transacao))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/saque")
    public ResponseEntity<TransacaoDTO> sacar(@Valid @RequestBody SaqueDTO dto) {
        return transacaoService.sacar(dto.contaId(), dto.valor(), dto.descricao())
                .map(this::toDTO)
                .map(transacao -> ResponseEntity.status(HttpStatus.CREATED).body(transacao))
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("/transferencia")
    public ResponseEntity<TransacaoDTO> transferir(@Valid @RequestBody TransferenciaDTO dto) {
        return transacaoService.transferir(
                        dto.contaOrigemId(),
                        dto.contaDestinoId(),
                        dto.valor(),
                        dto.descricao()
                )
                .map(this::toDTO)
                .map(transacao -> ResponseEntity.status(HttpStatus.CREATED).body(transacao))
                .orElse(ResponseEntity.badRequest().build());
    }

    private TransacaoDTO toDTO(Transacao transacao) {
        return new TransacaoDTO(
                transacao.getId(),
                transacao.getTipo(),
                transacao.getValor(),
                transacao.getContaOrigem() != null ? transacao.getContaOrigem().getId() : null,
                transacao.getContaOrigem() != null ? transacao.getContaOrigem().getUsuario().getNome() : null,
                transacao.getContaDestino() != null ? transacao.getContaDestino().getId() : null,
                transacao.getContaDestino() != null ? transacao.getContaDestino().getUsuario().getNome() : null,
                transacao.getDescricao(),
                transacao.getCreatedAt()
        );
    }
}