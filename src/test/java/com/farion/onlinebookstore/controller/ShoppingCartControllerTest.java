package com.farion.onlinebookstore.controller;

import static com.farion.onlinebookstore.util.ConstUtil.CART_ENDPOINT;
import static com.farion.onlinebookstore.util.ConstUtil.CLEAR_ENDPOINT;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.farion.onlinebookstore.dto.item.cartitem.CreateCartItemRequestDto;
import com.farion.onlinebookstore.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShoppingCartControllerTest {
    private static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext context) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Sql(scripts = "classpath:db/users/add-test-user.sql"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/users/remove-test-user.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @DisplayName("Get cart of authorized user")
    void getCart_authorizedUser_Success() throws Exception {
        Long expectedId = 1L;
        addUserToSecurityContextHolder(expectedId);

        mockMvc.perform(get(CART_ENDPOINT))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(expectedId))
                .andExpect(jsonPath("$.userId").value(expectedId))
                .andExpect(jsonPath("$.cartItems").isEmpty());
    }

    @Test
    @DisplayName("Can`t get cart because unauthorized user")
    void getCart_unauthorizedUser_ThrowException() throws Exception {
        mockMvc.perform(get(CART_ENDPOINT))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Clear cart of authorized user")
    void clearCart_authorizedUser_Success() throws Exception {
        addUserToSecurityContextHolder(1L);

        mockMvc.perform(delete(CART_ENDPOINT + CLEAR_ENDPOINT))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Can`t clear cart of unauthorized user")
    void clearCart_unauthorizedUser_ThrowException() throws Exception {
        mockMvc.perform(delete(CART_ENDPOINT + CLEAR_ENDPOINT))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Add book to the cart of authorized user")
    void addBookToCart_authorizedUser_Success() throws Exception {
        Long expectedBookId = 2L;
        int expectedQuantity = 4;
        CreateCartItemRequestDto requestDto = new CreateCartItemRequestDto();
        requestDto.setBookId(expectedBookId);
        requestDto.setQuantity(expectedQuantity);
        addUserToSecurityContextHolder(1L);

        mockMvc.perform(post(CART_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.bookId").value(expectedBookId))
                .andExpect(jsonPath("$.quantity").value(expectedQuantity));
    }

    @Test
    @DisplayName("Can`t add book to the cart of unauthorized user")
    void addBookToCart_unauthorizedUser_ThrowException() throws Exception {
        Long expectedBookId = 2L;
        int expectedQuantity = 4;
        CreateCartItemRequestDto requestDto = new CreateCartItemRequestDto();
        requestDto.setBookId(expectedBookId);
        requestDto.setQuantity(expectedQuantity);

        mockMvc.perform(post(CART_ENDPOINT))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Can`t add book to the cart because invalid data")
    void addBookToCart_invalidData_ThrowException() throws Exception {
        Long expectedBookId = 2L;
        int expectedQuantity = -5;
        CreateCartItemRequestDto requestDto = new CreateCartItemRequestDto();
        requestDto.setBookId(expectedBookId);
        requestDto.setQuantity(expectedQuantity);
        addUserToSecurityContextHolder(1L);

        mockMvc.perform(post(CART_ENDPOINT))
                .andExpect(status().isBadRequest());
    }



    private void addUserToSecurityContextHolder(Long id) {
        String email = "test@example.com";
        String role = "ROLE_USER";
        User user = new User();
        user.setId(id);
        user.setEmail(email);

        GrantedAuthority authority = new SimpleGrantedAuthority(role);
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(user, null,
                Collections.singleton(authority));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
