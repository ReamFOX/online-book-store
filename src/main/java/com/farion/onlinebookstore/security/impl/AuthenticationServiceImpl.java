package com.farion.onlinebookstore.security.impl;

import com.farion.onlinebookstore.dto.user.RegisterUserRequestDto;
import com.farion.onlinebookstore.dto.user.UserDto;
import com.farion.onlinebookstore.entity.User;
import com.farion.onlinebookstore.exception.RegistrationException;
import com.farion.onlinebookstore.mapper.UserMapper;
import com.farion.onlinebookstore.repository.user.UserRepository;
import com.farion.onlinebookstore.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto register(RegisterUserRequestDto requestDto) throws RegistrationException {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException("User with email "
                    + requestDto.getEmail() + " already exist");
        }
        User user = userMapper.toModel(requestDto);
        return userMapper.toDto(userRepository.save(user));
    }
}
