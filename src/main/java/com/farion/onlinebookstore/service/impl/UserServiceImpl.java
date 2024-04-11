package com.farion.onlinebookstore.service.impl;

import com.farion.onlinebookstore.dto.user.UserDto;
import com.farion.onlinebookstore.entity.User;
import com.farion.onlinebookstore.mapper.UserMapper;
import com.farion.onlinebookstore.repository.UserRepository;
import com.farion.onlinebookstore.service.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto save(User user) {
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
