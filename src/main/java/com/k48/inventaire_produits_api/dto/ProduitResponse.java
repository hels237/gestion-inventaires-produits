package com.k48.inventaire_produits_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProduitResponse {

    private Long id;
    private String nom;
    private Double prix;
    private Integer quantiteEnStock;

}