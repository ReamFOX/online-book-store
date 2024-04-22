package com.farion.onlinebookstore.mapper;

import com.farion.onlinebookstore.config.MapperConfig;
import com.farion.onlinebookstore.dto.ShoppingCartDto;
import com.farion.onlinebookstore.entity.ShoppingCart;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = CartItemMapper.class)
public interface ShoppingCartMapper {

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "cartItems", source = "cartItems", qualifiedByName = "fromItemsToDtos")
    ShoppingCartDto toDto(ShoppingCart cart);

    @AfterMapping
    default void setUserId(@MappingTarget ShoppingCartDto cartDto, ShoppingCart cart) {
        cartDto.setUserId(cart.getUser().getId());
    }
}
