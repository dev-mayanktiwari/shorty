package tech.mayanktiwari.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final String code;
    private final HttpStatus httpStatus;

    public BusinessException(String message, String code, HttpStatus status) {
        super(message);
        this.code = code;
        this.httpStatus = status;
    }

    public BusinessException(ErrorCodes errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.httpStatus = errorCode.getHttpStatus();
    }
}