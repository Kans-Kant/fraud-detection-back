package com.isfa.fr.fraudDetection.controller;

import com.isfa.fr.fraudDetection.constants.Paths;
import com.isfa.fr.fraudDetection.dto.UserDto;
import com.isfa.fr.fraudDetection.dto.request.RegisterUserDto;
import com.isfa.fr.fraudDetection.dto.response.LoginResponse;
import com.isfa.fr.fraudDetection.model.entities.User;
import com.isfa.fr.fraudDetection.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Paths.USER)
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public UserDto authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();
        UserDto userResponse = UserDto.builder().id(currentUser.getId())
                .email(currentUser.getEmail())
                .fullName(currentUser.getFullName())
                .createdAt(currentUser.getCreatedAt())
                .updatedAt(currentUser.getUpdatedAt())
                .build();

        return userResponse;
    }

    @GetMapping("/all")
    public List<UserDto> allUsers() {
        return userService.allUsers();
    }

    @PutMapping("/update")
    public UserDto update(@RequestBody RegisterUserDto registerUserDto) {
        UserDto userDto = userService.update(registerUserDto);

        return userDto;
    }

    @PostMapping( "/addUser")
    public UserDto signup(@RequestBody RegisterUserDto input) {
        return userService.addUser(input);
    }

    @PutMapping("/update/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody RegisterUserDto registerUserDto) {
        UserDto userDto = userService.updateUser(id, registerUserDto);

        return userDto;
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        return userService.delete(id);
    }

    @GetMapping("/details/{id}")
    public UserDto getUserDetails(@PathVariable Long id) {
        return userService.getUserDetails(id);
    }
}
