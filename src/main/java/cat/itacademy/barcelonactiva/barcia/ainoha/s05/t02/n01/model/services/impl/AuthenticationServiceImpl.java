package cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.services.impl;


import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.domain.User;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.request.SignInRequest;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.request.SignUpRequest;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.response.JwtAuthenticationResponse;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.repository.UserRepository;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.services.AuthenticationService;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        var user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .role(User.Role.USER).build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse signin(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
}