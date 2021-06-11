package com.devsuperior.bds02.exceptions.handler;

import com.devsuperior.bds02.exceptions.DataBaseException;
import com.devsuperior.bds02.exceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFoundHandler(EntityNotFoundException ex, HttpServletRequest request) {
        var status = HttpStatus.NOT_FOUND;
        var error = getStandardError(ex, status, request, "Resource not found");
        return ResponseEntity.status(status).body(error);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(DataBaseException.class)
    public ResponseEntity<StandardError> dataBaseExceptionHandler(DataBaseException ex, HttpServletRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        var error = getStandardError(ex, status, request, "Database exception");
        return ResponseEntity.status(status).body(error);
    }

    private StandardError getStandardError(Exception ex, HttpStatus status, HttpServletRequest request, String errorMsg) {
        var error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError(errorMsg);
        error.setMessage(ex.getMessage());
        error.setPath(request.getRequestURI());
        return error;
    }
}
