package com.alkemy.disney.auth.service;

import com.alkemy.disney.auth.dto.AuthenticationRequest;
import com.alkemy.disney.auth.dto.UserDTO;
import com.alkemy.disney.auth.entity.UserEntity;
import com.alkemy.disney.auth.repository.UserRepository;
import com.alkemy.disney.exception.ErrorEnum;
import com.alkemy.disney.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsCustomService implements UserDetailsService {

    private UserRepository userRepository;
    private EmailService emailService;
    private PasswordEncoder passwordEncoder;
    private JwtUtils jwtUtils;
    private AuthenticationManager authenticationManager;

    @Autowired
    public UserDetailsCustomService(UserRepository userRepository,
                                    EmailService emailService,
                                    PasswordEncoder passwordEncoder,
                                    JwtUtils jwtUtils,
                                    AuthenticationManager authenticationManager) {

        this.userRepository = userRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity entity = userRepository.findByUsername(username);

        if (entity == null){
            throw new UsernameNotFoundException(ErrorEnum.USER_OR_PASSWORD_INCORRECT.getMessage());
        }

        return User.withUsername(entity.getUsername())
                .password(entity.getPassword())
                .authorities(Collections.emptyList())
                .build();

    }

    public boolean save(UserDTO dto) {
        UserEntity entity = new UserEntity();

        entity.setUsername(dto.getUsername());
        String password = passwordEncoder.encode(dto.getPassword());
        entity.setPassword(password);

        this.userRepository.save(entity);

        if (userRepository.findByUsername(entity.getUsername()) != null) {
            emailService.sendWelcomeEmailTo(entity.getUsername());
        }

        return true;
    }

    public String tokenSignIn(AuthenticationRequest authRequest) throws Exception {
        UserDetails userDetails;
        try {
            Authentication auth = this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );
            userDetails = (UserDetails) auth.getPrincipal();

        } catch (BadCredentialsException e) {
            throw new Exception(ErrorEnum.USER_OR_PASSWORD_INCORRECT.getMessage(), e);
        }

        return this.jwtUtils.generateToken(userDetails);
    }
}
