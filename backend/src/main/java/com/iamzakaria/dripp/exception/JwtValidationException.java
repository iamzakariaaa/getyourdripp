package com.iamzakaria.dripp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
@Getter
@Setter

@AllArgsConstructor
public class JwtValidationException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private final String message;
    private final HttpStatus httpStatus;

}
