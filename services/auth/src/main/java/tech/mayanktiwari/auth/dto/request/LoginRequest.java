package tech.mayanktiwari.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class LoginRequest {
    @NotBlank(message = "identifier is required")
    String identifier;

    @NotBlank(message = "password is required")
    String password;
}
