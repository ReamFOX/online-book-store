package com.farion.onlinebookstore.security.impl;

import com.farion.onlinebookstore.dto.user.RegisterUserRequestDto;
import com.farion.onlinebookstore.dto.user.UserDto;
import com.farion.onlinebookstore.entity.Role;
import com.farion.onlinebookstore.entity.User;
import com.farion.onlinebookstore.exception.RegistrationException;
import com.farion.onlinebookstore.mapper.UserMapper;
import com.farion.onlinebookstore.repository.user.UserRepository;
import com.farion.onlinebookstore.security.AuthenticationService;
import com.farion.onlinebookstore.service.RoleService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Role.RoleName USER = Role.RoleName.USER;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto register(RegisterUserRequestDto requestDto) throws RegistrationException {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException("User with email "
                    + requestDto.getEmail() + " already exist");
        }
        User user = userMapper.toModel(requestDto);
        Role newUser = roleService.findByName(USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(newUser));
        return userMapper.toDto(userRepository.save(user));
    }
}
