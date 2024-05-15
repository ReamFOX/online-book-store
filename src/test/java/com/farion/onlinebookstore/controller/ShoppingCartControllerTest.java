package com.farion.onlinebookstore.controller;

import static com.farion.onlinebookstore.util.TestObjectMother.CART_ENDPOINT;
import static com.farion.onlinebookstore.util.TestObjectMother.CART_ITEM_ENDPOINT;
import static com.farion.onlinebookstore.util.TestObjectMother.CLEAR_ENDPOINT;
import static com.farion.onlinebookstore.util.TestObjectMother.SLASH;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.farion.onlinebookstore.dto.ShoppingCartDto;
import com.farion.onlinebookstore.dto.item.cartitem.CartItemDto;
import com.farion.onlinebookstore.dto.item.cartitem.CreateCartItemRequestDto;
import com.farion.onlinebookstore.dto.item.cartitem.UpdateCartItemDto;
import com.farion.onlinebookstore.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

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

    @Sql(scripts = "classpath:db/users/add-test-user.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/users/remove-test-user.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @DisplayName("Get cart of authorized user")
    void getCart_authorizedUser_Success() throws Exception {
        Long expectedId = 1L;
        Set<CartItemDto> expectedItems = Collections.emptySet();
        addUserToSecurityContextHolder(expectedId);

        ShoppingCartDto expected = new ShoppingCartDto();
        expected.setCartItems(expectedItems);
        expected.setId(expectedId);
        expected.setUserId(expectedId);

        MvcResult result = mockMvc.perform(get(CART_ENDPOINT))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        ShoppingCartDto actual = objectMapper
                .readValue(result.getResponse().getContentAsString(), ShoppingCartDto.class);

        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @Test
    @DisplayName("Can`t get cart because unauthorized user")
    void getCart_unauthorizedUser_ThrowException() throws Exception {
        mockMvc.perform(get(CART_ENDPOINT))
                .andExpect(status().isUnauthorized());
    }

    @Sql(scripts = "classpath:db/users/add-test-user.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/users/remove-test-user.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
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

    @Sql(scripts = "classpath:db/users/add-test-user.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/users/remove-test-user.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
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

    @Sql(scripts = "classpath:db/users/add-test-user.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/users/remove-test-user.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
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

    @Sql(scripts = {
            "classpath:db/users/add-test-user.sql",
            "classpath:db/cartItems/add-test-cart-item.sql"
    },
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:db/cartItems/remove-test-cart-item.sql",
            "classpath:db/users/remove-test-user.sql"
    },
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @DisplayName("Update quantity of a book in the cart of authorized user")
    void updateQuantity_authorizedUser_Success() throws Exception {
        Long expectedId = 1L;
        int expectedQuantity = 3;
        String expectedTitle = "To Kill a Mockingbird";

        CartItemDto expected = new CartItemDto();
        expected.setId(expectedId);
        expected.setBookTitle(expectedTitle);
        expected.setQuantity(expectedQuantity);
        expected.setBookId(expectedId);

        UpdateCartItemDto requestDto = new UpdateCartItemDto();
        requestDto.setQuantity(expectedQuantity);

        addUserToSecurityContextHolder(1L);

        MvcResult result = mockMvc.perform(
                        put(CART_ENDPOINT + CART_ITEM_ENDPOINT + SLASH + expectedId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestDto))
                )
                .andExpect(status().isOk())
                .andReturn();

        CartItemDto actual =
                objectMapper.readValue(result.getResponse().getContentAsString(),
                        CartItemDto.class);

        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @Sql(scripts = {
            "classpath:db/users/add-test-user.sql",
            "classpath:db/cartItems/add-test-cart-item.sql"
    },
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:db/cartItems/remove-test-cart-item.sql",
            "classpath:db/users/remove-test-user.sql"
    },
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @DisplayName("Delete book from the cart of authorized user")
    void deleteBookFromCart_authorizedUser_Success() throws Exception {
        long cartItemId = 1L;
        addUserToSecurityContextHolder(1L);

        mockMvc.perform(delete(CART_ENDPOINT + CART_ITEM_ENDPOINT + SLASH + cartItemId))
                .andExpect(status().isNoContent());
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
