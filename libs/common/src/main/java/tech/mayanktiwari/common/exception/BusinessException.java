package tech.mayanktiwari.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = lombok.AccessLevel.PUBLIC)
@Getter
public class BusinessException extends RuntimeException {
    final String code;
    final HttpStatus httpStatus;

    public BusinessException(String message, String code, HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }
}
