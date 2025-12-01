package com.isfa.fr.fraudDetection.service;

import com.isfa.fr.fraudDetection.dto.UserDto;
import com.isfa.fr.fraudDetection.dto.request.RegisterUserDto;

import java.util.List;

public interface UserService {

    List<UserDto> allUsers();

    UserDto update(RegisterUserDto registerUserDto);
    UserDto updateUser(Long id, RegisterUserDto registerUserDto);
    UserDto addUser(RegisterUserDto input);
    boolean delete (Long id);
    UserDto getUserDetails(Long id);
}
