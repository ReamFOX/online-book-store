package com.farion.onlinebookstore.mapper;

import com.farion.onlinebookstore.config.MapperConfig;
import com.farion.onlinebookstore.dto.item.CartItemDto;
import com.farion.onlinebookstore.dto.item.CreateCartItemRequestDto;
import com.farion.onlinebookstore.entity.CartItem;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class, uses = BookMapper.class)
public interface CartItemMapper {

    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.title", target = "bookTitle")
    CartItemDto toDto(CartItem item);

    @Mapping(target = "book", source = "bookId", qualifiedByName = "bookFromId")
    CartItem toModel(CreateCartItemRequestDto requestDto);

    @Mapping(source = "bookId", target = "book.id")
    @Mapping(source = "bookTitle", target = "book.title")
    CartItem toEntity(CartItemDto itemDto);

    @Named("fromItemsToDtos")
    default Set<CartItemDto> fromItemsToDtos(Set<CartItem> items) {
        return items.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    @Named("fromDtosToItems")
    default Set<CartItem> fromDtosToItems(Set<CartItemDto> itemDtos) {
        return itemDtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toSet());
    }
}
