package org.jugenfeier.contacts.controller.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles the {@link NoSuchElementException} by logging a warning and returning a 404 Not Found response.
     *
     * <p>This method is responsible for handling {@code NoSuchElementException} instances that may occur
     * during the execution of the application. It logs a warning message and responds with a 404 Not Found
     * status to indicate that the requested resource was not found.</p>
     *
     * @param e The {@code NoSuchElementException} that was thrown.
     * @return A {@link ResponseEntity} with a 404 Not Found status.
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(final NoSuchElementException e){
        log.warn("Unhandled NoSuchElementException thrown. Message: {}", e.getMessage());
        return ResponseEntity.notFound().build();
    }

    /**
     * Handles generic {@link Exception}s by logging a warning and returning a 500 Internal Server Error response.
     *
     * <p>This method is responsible for handling generic {@code Exception} instances that may occur during
     * the execution of the application. It logs a warning message and responds with a 500 Internal Server Error
     * status to indicate that an unexpected error occurred.</p>
     *
     * @param e The {@code Exception} that was thrown.
     * @return A {@link ResponseEntity} with a 500 Internal Server Error status.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(final Exception e){
        log.warn("Unhandled Exception thrown. Message: {}", e.getMessage());
        return ResponseEntity.internalServerError().build();
    }
}
