package com.nadson.bankapi.service;

import com.nadson.bankapi.model.Conta;
import com.nadson.bankapi.model.Usuario;
import com.nadson.bankapi.repository.ContaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ContaService{
    private final ContaRepository contaRepository;
    public ContaService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    public Conta criarConta(Usuario usuario){
        Conta conta = new Conta();
        conta.setNumeroConta(gerarNumeroConta());
        conta.setUsuario(usuario);
        return contaRepository.save(conta);
    }

    public Optional<Conta> buscarPorId(Long id){
        return contaRepository.findById(id);
    }

    public Optional<Conta> buscarPorNumeroConta(String numeroConta){
        return contaRepository.findByNumeroConta(numeroConta);
    }

    private String gerarNumeroConta(){
        String numeroConta;
        do{
            numeroConta = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (contaRepository.existsByNumeroConta(numeroConta));
        return numeroConta;
    }
}