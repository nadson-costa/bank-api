package com.nadson.bankapi.controller;

import com.nadson.bankapi.dto.AuthResponseDTO;
import com.nadson.bankapi.dto.LoginDTO;
import com.nadson.bankapi.dto.RegisterDTO;
import com.nadson.bankapi.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }


    @PostMapping("/registro")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterDTO dto){
        return authService.register(dto)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginDTO dto){
        return authService.login(dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }


}
