package com.isfa.fr.fraudDetection.controller;

import com.isfa.fr.fraudDetection.config.JwtService;
import com.isfa.fr.fraudDetection.constants.Paths;
import com.isfa.fr.fraudDetection.dto.request.LoginUserDto;
import com.isfa.fr.fraudDetection.dto.response.LoginResponse;
import com.isfa.fr.fraudDetection.exception.FraudDetectionException;
import com.isfa.fr.fraudDetection.model.entities.User;
import com.isfa.fr.fraudDetection.model.enums.ErrorCode;
import com.isfa.fr.fraudDetection.repository.UserRepository;
import com.isfa.fr.fraudDetection.service.AuthService;
import com.isfa.fr.fraudDetection.dto.request.RegisterUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Paths.AUTH)
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthService authenticationService;

    @PostMapping("/login")
    public LoginResponse authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.login(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = LoginResponse.builder().token(jwtToken)
                .expiresIn(jwtService.getExpirationTime()).build();

        return loginResponse;
    }

    @PostMapping( "/register")
    public LoginResponse signup(@RequestBody RegisterUserDto input) {

        User existUser = userRepository.findByEmail(input.getEmail()).orElse(null);

        if(existUser !=null){
            throw new FraudDetectionException(ErrorCode.ENTITY_EXIST, "User already exist!!!");
        }
        User user = User.builder()
                .fullName(input.getFullName())
                .email(input.getEmail())
                .enabled(true)
                .password(passwordEncoder.encode(input.getPassword())).build();

        userRepository.save(user);

        LoginUserDto loginUserDto = LoginUserDto.builder()
                .email(input.getEmail())
                .password(input.getPassword())
                .build();

        User authenticatedUser = authenticationService.login(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = LoginResponse.builder().token(jwtToken)
                .expiresIn(jwtService.getExpirationTime()).build();
        return loginResponse;
    }
}