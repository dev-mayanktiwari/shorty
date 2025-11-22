package tech.mayanktiwari.auth.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponseDto {
    int id;
    String username;
    String email;
    String name;
}
