package cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.service.impl;


import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.exceptions.UserAlreadyExistsException;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.domain.User;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.request.SignInRequest;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.request.SignUpRequest;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.response.JwtAuthenticationResponse;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.enums.Role;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.repository.UserRepository;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.service.AuthenticationService;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.enums.Role.USER;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        userRepository.findByEmail(request.getEmail()).ifPresent(user -> {throw new UserAlreadyExistsException("Email is already registered: " + user.getEmail());});
        User user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER).build();
        userRepository.save(user);
        String jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse signin(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + request.getEmail()));
        String jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
}