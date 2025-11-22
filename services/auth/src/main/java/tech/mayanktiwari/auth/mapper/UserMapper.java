package tech.mayanktiwari.auth.mapper;

import tech.mayanktiwari.auth.dto.response.RegisterRequest;
import tech.mayanktiwari.auth.dto.response.UserResponseDto;
import tech.mayanktiwari.auth.models.Users;

public class UserMapper {
    public static Users toEntity(RegisterRequest registerRequest) {
        return Users.builder()
                    .username(registerRequest.getUsername())
                    .email(registerRequest.getEmail())
                    .password(registerRequest.getPassword())
                    .name(registerRequest.getName())
                    .build();
    }

    public static UserResponseDto toResponseDto(Users user) {
        return UserResponseDto.builder()
                              .id(user.getId())
                              .username(user.getUsername())
                              .email(user.getEmail())
                              .name(user.getName())
                              .build();
    }
}
