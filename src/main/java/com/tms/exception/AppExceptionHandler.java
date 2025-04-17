package com.tms.exception;

import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(AppExceptionHandler.class);

    @ExceptionHandler(value = AgeException.class)
    public String ageExceptionHandler(AgeException exception, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_CONFLICT);
        return exception.getMessage();
    }

    @ExceptionHandler(value = Exception.class)
    public String allExceptionsHandler(Exception exception, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_CONFLICT);
        return exception.getMessage();
    }

    @ExceptionHandler(value = LoginUsedException.class)
    public ResponseEntity<String> loginUsedExceptionHandler(LoginUsedException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<String> entityNotFoundExceptionExceptionHandler(EntityNotFoundException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<String> accessDeniedExceptionHandler(AccessDeniedException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = JwtException.class)
    public ResponseEntity<String> jwtExceptionHandler(JwtException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
