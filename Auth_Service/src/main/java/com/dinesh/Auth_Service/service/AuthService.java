package com.dinesh.Auth_Service.service;

import com.dinesh.Auth_Service.Entity.User;
import com.dinesh.Auth_Service.exception.InvalidCredentialsException;
import com.dinesh.Auth_Service.exception.UserAlreadyExistsException;
import com.dinesh.Auth_Service.exception.UserNotFoundException;
import com.dinesh.Auth_Service.repository.UserRepository;
import com.dinesh.Auth_Service.request.LoginRequestDTO;
import com.dinesh.Auth_Service.util.JWTUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userrepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    public AuthService(UserRepository userrepository,
                       PasswordEncoder passwordEncoder,
                       JWTUtil jwtUtil){
        this.userrepository = userrepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public void saveNewUser(User user) {
        if (userrepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userrepository.save(user);
    }

    public User findByUsername(String username) {
        return userrepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found")
                );
    }

    public String login(LoginRequestDTO loginRequestDTO) {
        User user = findByUsername(loginRequestDTO.getUsername());
        if (!passwordEncoder.matches(
                        loginRequestDTO.getPassword(),
                        user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }
        return jwtUtil.generateToken(user.getId().toString(), user.getUsername());
    }
}
