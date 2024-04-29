package com.farion.onlinebookstore.exception;

import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityNotFoundException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String TIMESTAMP = "timestamp";
    private static final String STATUS = "status";
    private static final String ERRORS = "errors";
    private final Map<String, Object> body = new LinkedHashMap<>();

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(STATUS, HttpStatus.BAD_REQUEST);
        List<String> errors = ex.getBindingResult().getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        body.put(ERRORS, errors);
        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException e) {
        return getDefaultTemplate(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Object> handleSqlIntegrityConstraintViolationException(
            SQLIntegrityConstraintViolationException e) {
        return getDefaultTemplate(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<Object> handleRegistrationException(RegistrationException e) {
        return getDefaultTemplate(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCategoryIdsException.class)
    public ResponseEntity<Object> handleInvalidCategoryIdsException(
            InvalidCategoryIdsException e) {
        return getDefaultTemplate(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<Object> handleJwtException(
            JwtException e) {
        return getDefaultTemplate(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidStatusException.class)
    public ResponseEntity<Object> handleStatusNotFoundException(
            InvalidStatusException e) {
        return getDefaultTemplate(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyCartException.class)
    public ResponseEntity<Object> handleEmptyCartException(
            InvalidStatusException e) {
        return getDefaultTemplate(e, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> getDefaultTemplate(Throwable e, HttpStatus status) {
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(STATUS, status);
        body.put(ERRORS, e.getMessage());
        return new ResponseEntity<>(body, status);
    }

    private String getErrorMessage(ObjectError e) {
        if (e instanceof FieldError) {
            String field = ((FieldError) e).getField();
            String message = e.getDefaultMessage();
            return field + ": " + message;
        }
        return e.getDefaultMessage();
    }
}
