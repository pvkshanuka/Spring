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

    public String login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        return jwtProvider.generateToken(authenticate);
    }
}
