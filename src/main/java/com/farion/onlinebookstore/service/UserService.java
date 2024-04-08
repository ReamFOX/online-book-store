package com.farion.onlinebookstore.service;

import com.farion.onlinebookstore.dto.user.RegisterUserRequestDto;
import com.farion.onlinebookstore.dto.user.UserDto;
import com.farion.onlinebookstore.exception.RegistrationException;

public interface UserService {
    UserDto createUser(RegisterUserRequestDto requestDto) throws RegistrationException;


}
