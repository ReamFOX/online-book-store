package com.farion.onlinebookstore.controller;

import static com.farion.onlinebookstore.util.TestObjectMother.AUTH_ENDPOINT;
import static com.farion.onlinebookstore.util.TestObjectMother.LOGIN_ENDPOINT;
import static com.farion.onlinebookstore.util.TestObjectMother.REGISTRATION_ENDPOINT;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.farion.onlinebookstore.dto.user.UserDto;
import com.farion.onlinebookstore.dto.user.login.UserLoginRequestDto;
import com.farion.onlinebookstore.dto.user.register.RegisterUserRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerTest {
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
    @DisplayName("Authenticate valid credentials")
    void authenticate_ValidCredentials_ReturnsToken() throws Exception {
        UserLoginRequestDto requestDto =
                new UserLoginRequestDto("test@example.com", "ALABAMA1");

        String result = objectMapper.writeValueAsString(requestDto);
        System.out.println(result);
        mockMvc.perform(post(AUTH_ENDPOINT + LOGIN_ENDPOINT)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    @DisplayName("Authenticate invalid credentials")
    void authenticate_InvalidCredentials_ThrowException() throws Exception {
        UserLoginRequestDto requestDto =
                new UserLoginRequestDto("invaliduser@example.com", "invalidPassword");

        String result = objectMapper.writeValueAsString(requestDto);
        System.out.println(result);
        mockMvc.perform(post(AUTH_ENDPOINT + LOGIN_ENDPOINT)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Sql(scripts = "classpath:db/users/remove-test-user.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Register new user")
    @Test
    public void register_NewUser_Success() throws Exception {
        String expectedName = "test";
        String expectedEmail = "test@example.com";
        String password = "ALABAMA1";

        RegisterUserRequestDto requestDto =
                new RegisterUserRequestDto();
        requestDto.setEmail(expectedEmail);
        requestDto.setPassword(password);
        requestDto.setRepeatPassword(password);
        requestDto.setFirstName(expectedName);
        requestDto.setLastName(expectedName);

        UserDto expected = new UserDto();
        expected.setEmail(expectedEmail);
        expected.setFirstName(expectedName);
        expected.setLastName(expectedName);

        MvcResult result = mockMvc.perform(post(AUTH_ENDPOINT + REGISTRATION_ENDPOINT)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        UserDto actual =
                objectMapper.readValue(result.getResponse().getContentAsString(), UserDto.class);

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @DisplayName("Register new user with invalid data")
    @Test
    public void register_invalidData_ThrowException() throws Exception {
        String expectedName = "test";
        String invalidEmail = "test#example.com";
        String password = "somepassword";
        RegisterUserRequestDto requestDto =
                new RegisterUserRequestDto();
        requestDto.setEmail(invalidEmail);
        requestDto.setPassword(password);
        requestDto.setRepeatPassword(password);
        requestDto.setFirstName(expectedName);
        requestDto.setLastName(expectedName);

        mockMvc.perform(post(AUTH_ENDPOINT + REGISTRATION_ENDPOINT)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
