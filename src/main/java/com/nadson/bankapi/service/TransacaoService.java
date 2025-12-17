package com.nadson.bankapi.service;

import com.nadson.bankapi.model.Conta;
import com.nadson.bankapi.model.TipoTransacao;
import com.nadson.bankapi.model.Transacao;
import com.nadson.bankapi.repository.ContaRepository;
import com.nadson.bankapi.repository.TransacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final ContaRepository contaRepository;

    public TransacaoService(TransacaoRepository transacaoRepository, ContaRepository contaRepository) {
        this.transacaoRepository = transacaoRepository;
        this.contaRepository = contaRepository;
    }



    @Transactional
    public Optional<Transacao> depositar(Long contaId, BigDecimal valor, String descricao) {
        return contaRepository.findById(contaId)
                .map(conta -> {
                    conta.setSaldo(conta.getSaldo().add(valor));
                    contaRepository.save(conta);

                    Transacao transacao = new Transacao();
                    transacao.setTipo(TipoTransacao.DEPOSITO);
                    transacao.setValor(valor);
                    transacao.setContaDestino(conta);
                    transacao.setDescricao(descricao);

                    return transacaoRepository.save(transacao);
                });
    }

    @Transactional
    public Optional<Transacao> sacar(Long contaId, BigDecimal valor, String descricao) {
        return contaRepository.findById(contaId)
                .filter(conta -> conta.getSaldo().compareTo(valor) >= 0)
                .map(conta -> {
                    conta.setSaldo(conta.getSaldo().subtract(valor));
                    contaRepository.save(conta);

                    Transacao transacao = new Transacao();
                    transacao.setTipo(TipoTransacao.SAQUE);
                    transacao.setValor(valor);
                    transacao.setContaOrigem(conta);
                    transacao.setDescricao(descricao);

                    return transacaoRepository.save(transacao);
                });
    }

    @Transactional
    public Optional<Transacao> transferir(Long contaOrigemId, Long contaDestinoId, BigDecimal valor, String descricao) {
        Optional<Conta> origemOpt = contaRepository.findById(contaOrigemId);
        Optional<Conta> destinoOpt = contaRepository.findById(contaDestinoId);

        if(origemOpt.isEmpty() && destinoOpt.isEmpty()) {
            return Optional.empty();
        }

        Conta origem = origemOpt.get();
        Conta destino = destinoOpt.get();

        if (origem.getSaldo().compareTo(valor) < 0) {
            return Optional.empty();
        }

        origem.setSaldo(origem.getSaldo().subtract(valor));
        destino.setSaldo(destino.getSaldo().add(valor));
        contaRepository.save(origem);
        contaRepository.save(destino);

        Transacao transacao = new Transacao();
        transacao.setTipo(TipoTransacao.TRANSFERENCIA);
        transacao.setValor(valor);
        transacao.setContaOrigem(origem);
        transacao.setContaDestino(destino);
        transacao.setDescricao(descricao);

        return Optional.of(transacaoRepository.save(transacao));
    }

    public List<Transacao> buscarExtrato(Long contaId) {
        return transacaoRepository.findByContaOrigemIdOrContaDestinoIdOrderByCreatedAtDesc(contaId, contaId);
    }
}
