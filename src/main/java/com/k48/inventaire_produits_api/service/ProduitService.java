package com.k48.inventaire_produits_api.service;



import com.k48.inventaire_produits_api.dto.ProduitRequest;
import com.k48.inventaire_produits_api.dto.ProduitResponse;

import java.util.List;
import java.util.Optional;

public interface ProduitService {
    ProduitResponse createProduit(ProduitRequest produitRequest); // Prend un DTO Request, renvoie un DTO Response
    List<ProduitResponse> getAllProduits(); // Renvoie une liste de DTOs Response
    Optional<ProduitResponse> getProduitById(Long id); // Renvoie un Optional de DTO Response
    ProduitResponse updateProduit(Long id, ProduitRequest produitRequest); // Prend un DTO Request, renvoie un DTO Response
    void deleteProduit(Long id);
    List<ProduitResponse> getProduitsEnStockBas(int seuil); // Renvoie une liste de DTOs Response
    List<ProduitResponse> searchProduitsByNom(String nom); // Renvoie une liste de DTOs Response
}