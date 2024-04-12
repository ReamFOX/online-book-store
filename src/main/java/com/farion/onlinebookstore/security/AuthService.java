package com.farion.onlinebookstore.security;

import com.farion.onlinebookstore.dto.user.RegisterUserRequestDto;
import com.farion.onlinebookstore.dto.user.UserDto;
import com.farion.onlinebookstore.exception.RegistrationException;

public interface AuthService {
    UserDto register(RegisterUserRequestDto requestDto) throws RegistrationException;
}
