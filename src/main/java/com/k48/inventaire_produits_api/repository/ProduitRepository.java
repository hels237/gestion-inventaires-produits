package com.k48.inventaire_produits_api.repository;


import com.k48.inventaire_produits_api.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
    // Spring Data JPA générera automatiquement les méthodes CRUD (Create, Read, Update, Delete)
    // en se basant sur JpaRepository.

    // méthodes de recherche personnalisées :
    List<Produit> findByNomContainingIgnoreCase(String nom);

    List<Produit> findByQuantiteEnStockLessThan(Integer quantite);

    // Méthode pour trouver un produit par son nom (pour la vérification d'existence)
    Optional<Produit> findByNom(String nom);

}