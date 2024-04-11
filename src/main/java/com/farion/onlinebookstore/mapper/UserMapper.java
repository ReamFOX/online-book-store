package com.farion.onlinebookstore.mapper;

import com.farion.onlinebookstore.config.MapperConfig;
import com.farion.onlinebookstore.dto.user.UserDto;
import com.farion.onlinebookstore.dto.user.register.RegisterUserRequestDto;
import com.farion.onlinebookstore.entity.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserDto toDto(User user);

    User toModel(RegisterUserRequestDto requestDto);
}
