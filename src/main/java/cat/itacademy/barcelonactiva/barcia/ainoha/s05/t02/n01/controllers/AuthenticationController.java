package cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.controllers;


import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.request.SignInRequest;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.request.SignUpRequest;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.response.JwtAuthenticationResponse;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/game/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @Operation(summary = "SignUp new User")
    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody @Valid SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }
    @Operation(summary = "SignIn User")
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody @Valid SignInRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }
}
