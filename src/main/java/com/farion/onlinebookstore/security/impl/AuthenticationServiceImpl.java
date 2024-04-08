package com.farion.onlinebookstore.security.impl;

import com.farion.onlinebookstore.dto.user.RegisterUserRequestDto;
import com.farion.onlinebookstore.dto.user.UserDto;
import com.farion.onlinebookstore.exception.RegistrationException;
import com.farion.onlinebookstore.security.AuthenticationService;
import com.farion.onlinebookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;

    @Override
    public UserDto register(RegisterUserRequestDto requestDto) throws RegistrationException {
        if (userService)
        return userService.createUser(requestDto);
    }
}
