package tech.mayanktiwari.common.api;

import java.time.Instant;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ApiResponse<T> {
    boolean success;
    String message;
    T data;
    ApiError error;
    Instant timestamp;
    String path;
    Integer status;
}
