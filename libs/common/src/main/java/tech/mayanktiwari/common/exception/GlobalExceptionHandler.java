package tech.mayanktiwari.common.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import tech.mayanktiwari.common.api.ApiError;
import tech.mayanktiwari.common.api.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ApiResponse<Object>> buildErrorResponse(
            String message, String code, HttpStatus status, String path, String field
    ) {
        return ResponseEntity.status(status)
                             .body(ApiResponse.builder()
                                              .success(false)
                                              .message(message)
                                              .error(ApiError.builder()
                                                             .code(code)
                                                             .details(message)
                                                             .field(field)
                                                             .build())
                                              .path(path)
                                              .status(status.value())
                                              .timestamp(Instant.now())
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

        String field = ex.getBindingResult()
                         .getFieldError()
                         .getField();
        String message = ex.getBindingResult()
                           .getFieldError()
                           .getDefaultMessage();

        return buildErrorResponse(message, "VALIDATION_ERROR", HttpStatus.BAD_REQUEST, request.getRequestURI(), field);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleConstraintViolation(
            ConstraintViolationException ex, HttpServletRequest request
    ) {
        return buildErrorResponse(ex.getMessage(), "CONSTRAINT_ERROR", HttpStatus.BAD_REQUEST, request.getRequestURI(),
                                  null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleUnknownException(Exception ex, HttpServletRequest request) {

        return buildErrorResponse("Internal Server Error", "INTERNAL_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, request
                                                                                                                      .getRequestURI(),
                                  null);
    }
}