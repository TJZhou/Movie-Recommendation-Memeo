package com.tianju.memeo.exception;

import com.tianju.memeo.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class RestExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(value = {UserPermissionDeniedException.class})
    public ResponseEntity<Response> userPermissionDeniedException(UserPermissionDeniedException ex) {
        log.warn("Handle User Permission Denied: " + ex.getMessage());
        Response resp = new Response(ex.getCode(), "error", ex.getMessage());
        return new ResponseEntity<>(resp, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<Response> nullPointerException(NullPointerException ex) {
        log.warn("Handle Null pointer exception: " + ex.getMessage());
        Response resp = new Response(4004, "error", ex.getMessage());
        return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {NoSuchElementException.class})
    public ResponseEntity<Response> noSuchElementException(NoSuchElementException ex) {
        log.warn("Handle no such element exception: " + ex.getMessage());
        Response resp = new Response(4010, "error", ex.getMessage());
        return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ServletException.class})
    public ResponseEntity<Response> servletException(ServletException ex) {
        log.warn("Handling Servlet Exception: " + ex.getMessage());
        Response resp = new Response(5000, "error", ex.getMessage());
        return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Response> exception(Exception ex) {
        log.warn("Handling Internal Server Exception: " + ex.getMessage());
        Response resp = new Response(5001, "error", ex.getMessage());
        return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
