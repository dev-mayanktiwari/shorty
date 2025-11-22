package tech.mayanktiwari.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCodes {

    USER_NOT_FOUND("USR_404", "user not found", HttpStatus.NOT_FOUND),
    INVALID_CREDENTIALS("AUTH_401", "invalid credentials", HttpStatus.UNAUTHORIZED),
    USER_ALREADY_EXISTS("USR_409", "user already exists", HttpStatus.CONFLICT),
    ACCESS_DENIED("AUTH_403", "access denied", HttpStatus.FORBIDDEN),
    VALIDATION_FAILED("VALIDATION_ERROR", "validation failed", HttpStatus.BAD_REQUEST),
    INTERNAL_ERROR("INTERNAL_ERROR", "unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCodes(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.httpStatus = status;
    }
}