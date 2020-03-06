package lk.kusal.spring_project_practise.service;

import lk.kusal.spring_project_practise.dto.LoginRequest;
import lk.kusal.spring_project_practise.dto.RegisterRequest;
import lk.kusal.spring_project_practise.model.User;
import lk.kusal.spring_project_practise.repository.UserRepository;
import lk.kusal.spring_project_practise.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    public void signUp(RegisterRequest registerRequest) {

        User user = new User(registerRequest.getUsername(), encodePassword(registerRequest.getPassword()), registerRequest.getEmail());

        userRepository.save(user);

    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        System.out.println(loginRequest.getUsername());
        System.out.println(loginRequest.getPassword());
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        return new AuthenticationResponse(jwtProvider.generateToken(authenticate),loginRequest.getUsername());
//        return jwtProvider.generateToken(authenticate);
    }

    public Optional<org.springframework.security.core.userdetails.User> getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Optional.of(principal);
    }
}
