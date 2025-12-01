package com.isfa.fr.fraudDetection.service;

import com.isfa.fr.fraudDetection.model.entities.User;
import com.isfa.fr.fraudDetection.dto.request.LoginUserDto;

public interface AuthService {

    User login(LoginUserDto input);
}
