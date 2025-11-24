package tech.mayanktiwari.url.mapper;

import org.mapstruct.Mapper;

import tech.mayanktiwari.url.dto.response.ShortenUrlResponseDto;
import tech.mayanktiwari.url.models.Url;

@Mapper(componentModel = "spring")
public interface UrlMapper {
    ShortenUrlResponseDto toDto(Url url);
}
