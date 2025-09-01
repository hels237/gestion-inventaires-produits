package com.k48.inventaire_produits_api.handler;


import com.k48.inventaire_produits_api.exception.DuplicateResourceException;
import com.k48.inventaire_produits_api.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError; // Pour les erreurs de champ
import org.springframework.web.bind.MethodArgumentNotValidException; // Exception de validation
import org.springframework.web.bind.annotation.ControllerAdvice; // Annotation pour la gestion globale
import org.springframework.web.bind.annotation.ExceptionHandler; // Annotation pour gérer une exception spécifique
import org.springframework.web.context.request.WebRequest; // Pour les détails de la requête

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice // Indique que cette classe gère les exceptions pour tous les contrôleurs
public class GlobalExceptionHandler {

    // Gère les exceptions de ressources non trouvées (404 Not Found)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Gère les exceptions de ressources dupliquées (409 Conflict)
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<String> handleDuplicateResourceException(DuplicateResourceException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    // Gère les erreurs de validation des @Valid sur les DTOs (400 Bad Request)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField(); // Récupère le nom du champ
            String errorMessage = error.getDefaultMessage();    // Récupère le message d'erreur de validation
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST); // Code 400 Bad Request
    }

    // Gère toutes les autres exceptions non spécifiquement gérées (500 Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex, WebRequest request) {
        // Pour la production, on ne voudrait pas exposer ex.getMessage() directement pour des raisons de sécurité.
        // on pourrait logger l'erreur et renvoyer un message générique.
        return new ResponseEntity<>("Une erreur inattendue est survenue : " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}