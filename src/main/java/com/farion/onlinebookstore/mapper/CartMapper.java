package com.farion.onlinebookstore.mapper;

import com.farion.onlinebookstore.config.MapperConfig;
import com.farion.onlinebookstore.dto.ShoppingCartDto;
import com.farion.onlinebookstore.entity.ShoppingCart;
import com.farion.onlinebookstore.entity.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface CartMapper {
    @Mapping(target = "userId", ignore = true)
    ShoppingCartDto toDto(ShoppingCart cart);

    @AfterMapping
    default void setUserId(@MappingTarget ShoppingCartDto cartDto, ShoppingCart cart) {
        cartDto.setUserId(cart.getUser().getId());
    }

    @AfterMapping
    default void setUser(@MappingTarget ShoppingCart cart, ShoppingCartDto cartDto) {
        cart.setUser(new User(cartDto.getUserId()));
    }

    @Mapping(target = "user", ignore = true)
    ShoppingCart toEntity(ShoppingCartDto cartDto);
}
