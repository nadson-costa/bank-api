package com.nadson.bankapi.service;

import com.nadson.bankapi.model.Conta;
import com.nadson.bankapi.model.Usuario;
import com.nadson.bankapi.repository.ContaRepository;
import com.nadson.bankapi.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ContaService{
    private final ContaRepository contaRepository;
    private final UsuarioRepository usuarioRepository;

    public ContaService(ContaRepository contaRepository, UsuarioRepository usuarioRepository) {
        this.contaRepository = contaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Optional<Conta> criarConta(Long usuarioId){
        return usuarioRepository.findById(usuarioId)
                .map(usuario -> {
                    Conta conta = new Conta();
                    conta.setNumeroConta(gerarNumeroConta());
                    conta.setUsuario(usuario);
                    return contaRepository.save(conta);

                });
    }

    public Optional<Conta> buscarPorEmail(String email){
        return usuarioRepository.findByEmail(email)
                .flatMap(usuario -> contaRepository.findByUsuarioId(usuario.getId()));
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