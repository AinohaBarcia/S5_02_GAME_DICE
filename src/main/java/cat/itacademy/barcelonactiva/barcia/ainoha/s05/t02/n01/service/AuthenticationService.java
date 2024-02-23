package cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.service;

import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.request.SignInRequest;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.request.SignUpRequest;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);
    JwtAuthenticationResponse signin(SignInRequest request);
}