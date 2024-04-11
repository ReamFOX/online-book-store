package com.farion.onlinebookstore.security;

import com.farion.onlinebookstore.dto.user.login.UserLoginRequestDto;
import com.farion.onlinebookstore.dto.user.login.UserLoginResponseDto;
import com.farion.onlinebookstore.dto.user.register.RegisterUserRequestDto;
import com.farion.onlinebookstore.dto.user.UserDto;
import com.farion.onlinebookstore.exception.RegistrationException;

public interface AuthService {
    UserDto register(RegisterUserRequestDto requestDto) throws RegistrationException;

    UserLoginResponseDto authenticate(UserLoginRequestDto requestDto);
}
