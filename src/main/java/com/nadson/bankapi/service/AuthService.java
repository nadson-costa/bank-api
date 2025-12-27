package com.nadson.bankapi.service;

import com.nadson.bankapi.dto.AuthResponseDTO;
import com.nadson.bankapi.dto.LoginDTO;
import com.nadson.bankapi.dto.RegisterDTO;
import com.nadson.bankapi.model.Usuario;
import com.nadson.bankapi.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ContaService contaService;


    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtService jwtService, ContaService contaService){
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.contaService = contaService;
    }

    public Optional<AuthResponseDTO> register(RegisterDTO dto) {
        if (usuarioRepository.findByEmail(dto.email()).isPresent()) {
            return Optional.empty();
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenhaHash(passwordEncoder.encode(dto.senha()));
        usuarioRepository.save(usuario);

        contaService.criarConta(usuario.getId());

        String token = jwtService.generateToken(usuario.getEmail());
        return Optional.of(new AuthResponseDTO(token, usuario.getEmail(), usuario.getNome()));
    }

    public Optional<AuthResponseDTO> login(LoginDTO dto){
        return usuarioRepository.findByEmail(dto.email())
                .filter(usuario -> passwordEncoder.matches(dto.senha(), usuario.getSenhaHash()))
                .map(usuario -> {
                    String token = jwtService.generateToken(usuario.getEmail());
                    return new AuthResponseDTO(token, usuario.getEmail(), usuario.getNome());
                });
    }



}
