package tech.mayanktiwari.common.exception;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import tech.mayanktiwari.common.api.ApiError;
import tech.mayanktiwari.common.api.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ApiResponse<Object>> buildErrorResponse(
            String message, String code, HttpStatus status, String path, ApiError errorBody
    ) {
        return ResponseEntity.status(status)
                             .body(ApiResponse.builder()
                                              .success(false)
                                              .message(message)
                                              .error(errorBody != null ?
                                                      errorBody :
                                                      ApiError.builder()
                                                              .code(code)
                                                              .details(message)
                                                              .build())
                                              .timestamp(Instant.now())
                                              .status(status.value())
                                              .path(path)
                                              .build());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Object>> handleBusinessException(
            BusinessException ex, HttpServletRequest request
    ) {

        return buildErrorResponse(ex.getMessage(), ex.getCode(), ex.getHttpStatus(), request.getRequestURI(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationError(
            MethodArgumentNotValidException ex, HttpServletRequest request
    ) {

        var fieldErrors = ex.getBindingResult()
                            .getFieldErrors()
                            .stream()
                            .map(error -> ApiError.FieldErrorDetail.builder()
                                                                   .field(error.getField())
                                                                   .message(error.getDefaultMessage())
                                                                   .build())
                            .collect(Collectors.toList());

        var error = ApiError.builder()
                            .code("VALIDATION_ERROR")
                            .details("Validation failed")
                            .fields(fieldErrors)
                            .build();

        return buildErrorResponse("Validation failed", "VALIDATION_ERROR", HttpStatus.BAD_REQUEST, request
                                                                                                          .getRequestURI(),
                                  error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleUnexpectedException(Exception ex, HttpServletRequest request) {

        ex.printStackTrace();

        var error = ApiError.builder()
                            .code("INTERNAL_ERROR")
                            .details("Unexpected error occurred")
                            .build();

        return buildErrorResponse("Something went wrong on our side", "INTERNAL_ERROR",
                                  HttpStatus.INTERNAL_SERVER_ERROR, request.getRequestURI(), error);
    }
}