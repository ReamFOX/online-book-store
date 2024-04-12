package com.farion.onlinebookstore.dto.user.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginRequestDto(
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Size(min = 8, message = "Password must be at least 8 characters long")
        String password
) {
}
