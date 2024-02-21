package cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.controllers;


import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.request.SignInRequest;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.request.SignUpRequest;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.response.JwtAuthenticationResponse;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/game/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody @Valid SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody @Valid SignInRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }
}
