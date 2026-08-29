package com.dinesh.Auth_Service.service;

import com.dinesh.Auth_Service.Entity.User;
import com.dinesh.Auth_Service.repository.userRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final userRepository userrepository;

    public AuthService(userRepository userrepository){
        this.userrepository = userrepository;
    }

    public void signUp(User user) {
        userrepository.save(user);
    }
}
