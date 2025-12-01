package com.nadson.bankapi.repository;

import com.nadson.bankapi.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContaRepository extends JpaRepository<Conta, Long> {
    Optional<Conta>  findByNumeroConta(String numeroConta);
    boolean existsByNumeroConta(String numeroConta);
}
