package com.isfa.fr.fraudDetection.service.impl;

import com.isfa.fr.fraudDetection.dto.UserDto;
import com.isfa.fr.fraudDetection.dto.request.RegisterUserDto;
import com.isfa.fr.fraudDetection.exception.FraudDetectionException;
import com.isfa.fr.fraudDetection.model.entities.User;
import com.isfa.fr.fraudDetection.model.enums.ErrorCode;
import com.isfa.fr.fraudDetection.repository.UserRepository;
import com.isfa.fr.fraudDetection.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> allUsers() {

        List<UserDto> userDtos = userRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
        return userDtos;
    }

    @Override
    public UserDto update(RegisterUserDto registerUserDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        User savedUser = userRepository.findById(currentUser.getId()).orElse(null);

        if(savedUser !=null){
            savedUser.setUpdatedAt(LocalDateTime.now());
            savedUser.setFullName(registerUserDto.getFullName());
            savedUser.setEmail(registerUserDto.getEmail());
            savedUser = userRepository.save(savedUser);

            if(!registerUserDto.getPassword().isEmpty()){
                savedUser.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
            }

        }

        return toDto(savedUser);
    }

    @Override
    public UserDto updateUser(Long id, RegisterUserDto registerUserDto) {


        User savedUser = userRepository.findById(id).orElse(null);

        if(savedUser !=null){
            savedUser.setUpdatedAt(LocalDateTime.now());
            savedUser.setFullName(registerUserDto.getFullName());
            savedUser.setEmail(registerUserDto.getEmail());
            savedUser = userRepository.save(savedUser);

            if(!registerUserDto.getPassword().isEmpty()){
                savedUser.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
            }
        }

        return toDto(savedUser);
    }

    UserDto toDto(User user){
        return  UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .enabled(user.isEnabled())
                .createdAt(user.getCreatedAt()).updatedAt(user.getUpdatedAt())
                .build();
    }

    public UserDto addUser(RegisterUserDto input) {

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

        return toDto(user);
    }

    @Override
    public boolean delete(Long id) {
        userRepository.deleteById(id);
        return true;
    }

    @Override
    public UserDto getUserDetails(Long id){

        User savedUser = userRepository.findById(id).orElse(null);
        if(savedUser ==null){
            throw new FraudDetectionException(ErrorCode.ENTITY_NOT_FOUND, "User not exist !!!");
        }
        return toDto(savedUser);
    }

}
