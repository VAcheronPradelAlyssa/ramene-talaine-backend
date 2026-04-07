package fr.ramenetalaine.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ListingNotFoundException.class)
    public ResponseEntity<String> handleListingNotFound(ListingNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException exception) {
        String message = exception.getMessage();
        if ("Token invalide".equals(message)
                || "Utilisateur introuvable pour ce token".equals(message)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
}
