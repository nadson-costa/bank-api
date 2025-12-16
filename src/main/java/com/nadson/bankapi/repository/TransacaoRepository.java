package com.nadson.bankapi.repository;

import com.nadson.bankapi.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao,Long> {
    List<Transacao> findByContaOrigemIdOrContaDestinoIdOrderByCreatedAtDesc(Long contaOrigemId, Long contaDesino);

}
