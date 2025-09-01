package com.k48.inventaire_produits_api.service;


import com.k48.inventaire_produits_api.dto.ProduitRequest;
import com.k48.inventaire_produits_api.dto.ProduitResponse;
import com.k48.inventaire_produits_api.entity.Produit;
import com.k48.inventaire_produits_api.exception.DuplicateResourceException;
import com.k48.inventaire_produits_api.exception.ResourceNotFoundException;
import com.k48.inventaire_produits_api.repository.ProduitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProduitServiceImpl implements ProduitService {

    private final ProduitRepository produitRepository;

    public ProduitServiceImpl(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    // --- Méthodes de conversion (mapper) ---
    private Produit mapToProduit(ProduitRequest request) {
        Produit produit = new Produit();
        produit.setNom(request.getNom());
        produit.setPrix(request.getPrix());
        produit.setQuantiteEnStock(request.getQuantiteEnStock());
        return produit;
    }

    private ProduitResponse mapToProduitResponse(Produit produit) {
        return new ProduitResponse(
                produit.getId(),
                produit.getNom(),
                produit.getPrix(),
                produit.getQuantiteEnStock()
        );
    }
    // --- Fin des méthodes de conversion ---


    @Override
    public ProduitResponse createProduit(ProduitRequest produitRequest) {
        Optional<Produit> existingProduit = produitRepository.findByNom(produitRequest.getNom());
        if (existingProduit.isPresent()) {
            throw new DuplicateResourceException("Un produit avec le nom '" + produitRequest.getNom() + "' existe déjà.");
        }
        Produit produitToSave = mapToProduit(produitRequest); // Convertit DTO en entité
        Produit savedProduit = produitRepository.save(produitToSave);
        return mapToProduitResponse(savedProduit); // Convertit entité en DTO de réponse
    }

    @Override
    public List<ProduitResponse> getAllProduits() {
        return produitRepository.findAll().stream()
                .map(this::mapToProduitResponse) // Convertit chaque entité en DTO de réponse
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProduitResponse> getProduitById(Long id) {
        return produitRepository.findById(id)
                .map(this::mapToProduitResponse); // Convertit l'entité trouvée (si présente) en DTO
    }

    @Override
    public ProduitResponse updateProduit(Long id, ProduitRequest produitRequest) {
        Produit existingProduit = produitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé avec l'id : " + id));

        if (!existingProduit.getNom().equals(produitRequest.getNom())) {
            Optional<Produit> produitWithNewName = produitRepository.findByNom(produitRequest.getNom());
            if (produitWithNewName.isPresent() && !produitWithNewName.get().getId().equals(id)) {
                throw new DuplicateResourceException("Un autre produit avec le nom '" + produitRequest.getNom() + "' existe déjà.");
            }
        }

        // Met à jour les champs de l'entité existante avec les données du DTO Request
        existingProduit.setNom(produitRequest.getNom());
        existingProduit.setPrix(produitRequest.getPrix());
        existingProduit.setQuantiteEnStock(produitRequest.getQuantiteEnStock());

        Produit updatedProduit = produitRepository.save(existingProduit);
        return mapToProduitResponse(updatedProduit); // Convertit entité en DTO de réponse
    }

    @Override
    public void deleteProduit(Long id) {
        // Vérifie l'existence avant de supprimer
        produitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé avec l'id : " + id));
        produitRepository.deleteById(id);
    }

    @Override
    public List<ProduitResponse> getProduitsEnStockBas(int seuil) {
        return produitRepository.findByQuantiteEnStockLessThan(seuil).stream()
                .map(this::mapToProduitResponse) // Convertit chaque entité en DTO de réponse
                .collect(Collectors.toList());
    }

    @Override
    public List<ProduitResponse> searchProduitsByNom(String nom) {
        return produitRepository.findByNomContainingIgnoreCase(nom).stream()
                .map(this::mapToProduitResponse) // Convertit chaque entité en DTO de réponse
                .collect(Collectors.toList());
    }
}
