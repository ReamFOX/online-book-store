package com.farion.onlinebookstore.security.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.farion.onlinebookstore.dto.user.UserDto;
import com.farion.onlinebookstore.dto.user.login.UserLoginRequestDto;
import com.farion.onlinebookstore.dto.user.login.UserLoginResponseDto;
import com.farion.onlinebookstore.dto.user.register.RegisterUserRequestDto;
import com.farion.onlinebookstore.entity.Role;
import com.farion.onlinebookstore.entity.User;
import com.farion.onlinebookstore.mapper.UserMapper;
import com.farion.onlinebookstore.repository.UserRepository;
import com.farion.onlinebookstore.service.RoleService;
import com.farion.onlinebookstore.service.ShoppingCartService;
import com.farion.onlinebookstore.util.JwtUtil;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private ShoppingCartService shoppingCartService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Register new user")
    @Test
    public void register_NewUser_Success() throws Exception {
        RegisterUserRequestDto requestDto = new RegisterUserRequestDto();
        requestDto.setEmail("test@example.com");
        requestDto.setPassword("password");

        User user = new User();
        user.setEmail(requestDto.getEmail());
        user.setPassword(requestDto.getPassword());

        UserDto expected = new UserDto();
        expected.setEmail(requestDto.getEmail());

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userMapper.toModel(any(RegisterUserRequestDto.class))).thenReturn(user);
        when(roleService.findByName(any())).thenReturn(new Role());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toDto(any(User.class))).thenReturn(expected);

        UserDto result = authService.register(requestDto);

        assertNotNull(result);
        assertEquals(requestDto.getEmail(), result.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
        verify(shoppingCartService, times(1)).registerNewShoppingCart(any(User.class));
    }

    @DisplayName("Authenticate valid credentials")
    @Test
    public void authenticate_ValidCredentials_ReturnsToken() {
        UserLoginRequestDto requestDto = new UserLoginRequestDto("test@example.com", "password");
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(requestDto.email());
        when(authenticationManager.authenticate(
                any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtUtil.generateToken(anyString())).thenReturn("jwtToken");

        UserLoginResponseDto result = authService.authenticate(requestDto);

        assertNotNull(result);
        assertNotNull(result.token());
        verify(jwtUtil, times(1)).generateToken(anyString());
    }
}
