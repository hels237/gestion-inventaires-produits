package com.k48.inventaire_produits_api.controller;


import com.k48.inventaire_produits_api.dto.ProduitRequest;
import com.k48.inventaire_produits_api.dto.ProduitResponse;
import com.k48.inventaire_produits_api.exception.ResourceNotFoundException;
import com.k48.inventaire_produits_api.service.ProduitService;
import jakarta.validation.Valid; // Pour activer la validation des DTOs
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/produits")
@RequiredArgsConstructor
public class ProduitController {

    private final ProduitService produitService;


    @PostMapping
    public ResponseEntity<ProduitResponse> createProduit(@Valid @RequestBody ProduitRequest produitRequest) {
        // La validation se fait automatiquement grâce à @Valid
        ProduitResponse newProduit = produitService.createProduit(produitRequest);
        return new ResponseEntity<>(newProduit, HttpStatus.CREATED); // Code 201 Created
    }


    @GetMapping
    public ResponseEntity<List<ProduitResponse>> getAllProduits() {
        List<ProduitResponse> produits = produitService.getAllProduits();
        return new ResponseEntity<>(produits, HttpStatus.OK); // Code 200 OK
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProduitResponse> getProduitById(@PathVariable Long id) {
        return produitService.getProduitById(id)
                .map(produit -> new ResponseEntity<>(produit, HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé avec l'id : " + id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProduitResponse> updateProduit(@PathVariable Long id, @Valid @RequestBody ProduitRequest produitRequest) {
        ProduitResponse updatedProduit = produitService.updateProduit(id, produitRequest);
        return new ResponseEntity<>(updatedProduit, HttpStatus.OK); // Code 200 OK
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable Long id) {
        produitService.deleteProduit(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Code 204 No Content
    }

    @GetMapping("/stock-bas")
    public ResponseEntity<List<ProduitResponse>> getProduitsEnStockBas(@RequestParam(defaultValue = "5") int seuil) {
        List<ProduitResponse> produitsEnStockBas = produitService.getProduitsEnStockBas(seuil);
        return new ResponseEntity<>(produitsEnStockBas, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProduitResponse>> searchProduitsByNom(@RequestParam String nom) {
        List<ProduitResponse> produitsTrouves = produitService.searchProduitsByNom(nom);
        return new ResponseEntity<>(produitsTrouves, HttpStatus.OK);
    }

    // --- Gestion des exceptions globales (optionnel, mais bonne pratique) ---
    // Tu as déjà la @ResponseStatus sur tes classes d'exception,
    // mais tu peux aussi avoir un @ControllerAdvice pour gérer toutes les exceptions au même endroit.
    // Pour ce projet, les @ResponseStatus sont suffisantes pour les 404 et 409.
    // Si tu veux une gestion plus fine des erreurs de validation (400 Bad Request), tu peux ajouter :

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}
