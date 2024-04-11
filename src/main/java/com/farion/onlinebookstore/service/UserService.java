package com.farion.onlinebookstore.service;

import com.farion.onlinebookstore.dto.user.UserDto;
import com.farion.onlinebookstore.entity.User;
import java.util.Optional;

public interface UserService {
    UserDto save(User user);

    Optional<User> findByEmail(String email);
}
