package com.farion.onlinebookstore.dto.user.register;

import com.farion.onlinebookstore.lib.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@FieldMatch(first = "password", second = "repeatPassword", message = "Passwords must match")
public class RegisterUserRequestDto {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
    private String repeatPassword;
    @NotBlank
    @Size(max = 255, message = "First name can't be longer than 255 characters")
    private String firstName;
    @NotBlank
    @Size(max = 255, message = "Second name can't be longer than 255 characters")
    private String lastName;
    @Size(max = 255, message = "Shipping address can't be longer than 255 characters")
    private String shippingAddress;
}
