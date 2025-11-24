package tech.mayanktiwari.auth.mapper;

import org.mapstruct.Mapper;

import tech.mayanktiwari.auth.dto.response.UserResponseDto;
import tech.mayanktiwari.database.entities.User;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    UserResponseDto toDto(User user);
}
