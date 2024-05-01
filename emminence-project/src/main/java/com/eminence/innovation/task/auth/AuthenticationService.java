package com.eminence.innovation.task.auth;

import com.eminence.innovation.task.exceptionHandler.UserAlreadyRegisteredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eminence.innovation.task.DTO.LoginUserDto;
import com.eminence.innovation.task.DTO.RegisterUserDto;
import com.eminence.innovation.task.DTO.UserRoles;
import com.eminence.innovation.task.model.User;
import com.eminence.innovation.task.repository.UserRepository;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterUserDto input) {
        User user = new User();
        user.setFullName(input.getFullName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        if (!input.getEmail().startsWith("admin")) {
            user.setRole(UserRoles.ROLE_USER);
        } else {
            user.setRole(UserRoles.ROLE_ADMIN);
        }
        try {
            return userRepository.save(user);
        } catch (Exception ex) {
            throw new UserAlreadyRegisteredException("User Already Registered with email " + input.getEmail(), ex);
        }
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));
        return userRepository.findByEmail(input.getEmail()).orElseThrow();
    }

}
