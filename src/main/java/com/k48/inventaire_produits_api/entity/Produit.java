package com.k48.inventaire_produits_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "produits") // Nom de la table dans la base de données
@Data // Génère getters, setters, toString, equals, hashCode par Lombok
@NoArgsConstructor // Génère un constructeur sans arguments par Lombok
@AllArgsConstructor // Génère un constructeur avec tous les arguments par Lombok

public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) //  contrainte de DB
    private String nom;

    private Double prix;

    private Integer quantiteEnStock;
}
