package tech.mayanktiwari.common.builder;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import tech.mayanktiwari.common.api.ApiError;
import tech.mayanktiwari.common.api.ApiResponse;
import tech.mayanktiwari.common.exception.BusinessException;

public class ResponseBuilder {
    private static String getCurrentRequestPath() {
        var requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes servletRequestAttributes) {
            return servletRequestAttributes.getRequest()
                                           .getRequestURI();
        }
        return "/unknown";
    }

    public static <T> ApiResponse<T> success(T data, String message, HttpStatus status) {
        return ApiResponse.<T>builder()
                          .success(true)
                          .message(message)
                          .data(data)
                          .status(status.value())
                          .path(getCurrentRequestPath())
                          .timestamp(Instant.now())
                          .build();
    }

    public static <T> ApiResponse<T> success(String message, HttpStatus status) {
        return ApiResponse.<T>builder()
                          .success(true)
                          .message(message)
                          .status(status.value())
                          .path(getCurrentRequestPath())
                          .timestamp(Instant.now())
                          .build();
    }

    public static <T> ApiResponse<T> error(String message, String code, HttpStatus status) {
        return ApiResponse.<T>builder()
                          .success(false)
                          .message(message)
                          .error(ApiError.builder()
                                         .code(code)
                                         .details(message)
                                         .build())
                          .status(status.value())
                          .path(getCurrentRequestPath())
                          .timestamp(Instant.now())
                          .build();
    }

    public static <T> ApiResponse<T> error(
            String message, String code, HttpStatus status, List<ApiError.FieldErrorDetail> fields
    ) {
        return ApiResponse.<T>builder()
                          .success(false)
                          .message(message)
                          .error(ApiError.builder()
                                         .code(code)
                                         .details(message)
                                         .fields(fields)
                                         .build())
                          .status(status.value())
                          .path(getCurrentRequestPath())
                          .timestamp(Instant.now())
                          .build();
    }

    public static <T> ApiResponse<T> error(BusinessException ex) {
        return error(ex.getMessage(), ex.getCode(), ex.getHttpStatus());
    }
}
