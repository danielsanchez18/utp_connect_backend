package com.nova.team.utp_connect_backend.servicesImpl;

import com.nova.team.utp_connect_backend.enities.User;
import com.nova.team.utp_connect_backend.repositories.UserRepository;
import com.nova.team.utp_connect_backend.services.AuthService;
import com.nova.team.utp_connect_backend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }
        return jwtUtil.generateToken(user.getEmail());
    }

    @Override
    public User currentUser(String token) {
        String email = jwtUtil.getUsernameFromToken(token);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}