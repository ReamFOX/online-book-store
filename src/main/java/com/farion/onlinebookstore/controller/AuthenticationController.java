package com.farion.onlinebookstore.controller;

import com.farion.onlinebookstore.dto.user.UserDto;
import com.farion.onlinebookstore.dto.user.login.UserLoginRequestDto;
import com.farion.onlinebookstore.dto.user.login.UserLoginResponseDto;
import com.farion.onlinebookstore.dto.user.register.RegisterUserRequestDto;
import com.farion.onlinebookstore.exception.RegistrationException;
import com.farion.onlinebookstore.security.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Tag(name = "Authentication management", description = "Endpoints for authentication")
@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Register a new user")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto register(@RequestBody @Valid RegisterUserRequestDto requestDto)
            throws RegistrationException {
        return authService.register(requestDto);
    }

    @PostMapping("/login")
    @Operation(summary = "Login as a user", description = "Endpoint for login as a user")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        return authService.authenticate(requestDto);
    }
}
