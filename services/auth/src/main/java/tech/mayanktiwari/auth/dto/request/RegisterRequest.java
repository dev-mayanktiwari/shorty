package tech.mayanktiwari.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class RegisterRequest {
    @NotBlank(message = "username is required")
    @Size(min = 3, max = 50, message = "username must be between 3 and 50 characters")
    String username;

    @NotBlank(message = "email is required")
    @Size(max = 100, message = "email must be less than 100 characters")
    String email;

    @NotBlank(message = "password is required")
    @Size(min = 6, max = 100, message = "password must be between 6 and 100 characters")
    String password;

    @NotBlank(message = "name is required")
    @Size(max = 100, message = "name must be less than 100 characters")
    String name;
}
