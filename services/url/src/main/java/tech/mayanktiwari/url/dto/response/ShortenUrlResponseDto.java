package tech.mayanktiwari.url.dto.response;

import lombok.experimental.FieldDefaults;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ShortenUrlResponseDto {
    String shortUrl;
    String longUrl;
    String userId;
}
