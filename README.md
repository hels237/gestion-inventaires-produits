# gestion-inventaires-produits

# 📦 Inventory Management API

Une application RESTful développée en Java avec Spring Boot permettant de gérer un inventaire de produits. Elle fournit des endpoints pour créer, lire, mettre à jour et supprimer des produits, ainsi que pour suivre les stocks faibles.

---

## 🚀 Fonctionnalités

- 🔍 Consulter tous les produits
- ➕ Créer un nouveau produit
- ✏️ Mettre à jour un produit existant
- ❌ Supprimer un produit
- ⚠️ Lister les produits en faible stock (stock < 5)
- 🔍 Rechercher des produits par nom

---

## 🛠️ Technologies utilisées

- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Validation
- PostgreSQL
- Swagger (OpenAPI 3) pour la documentation
- Lombok
- Maven 
- IDE recommandé : IntelliJ IDEA 

---

## 📂 Structure des classes principales

| Classe              | Description                                  |
|---------------------|----------------------------------------------|
| `ProduitRequest`    | DTO pour les requêtes de création/modification |
| `ProduitResponse`   | DTO pour les réponses contenant les produits |
| `ProduitController` | Expose les endpoints REST de l'API          |
| `ProduitService`    | Contient la logique métier liée aux produits|
| `Produit`           | Entité JPA représentant un produit          |

---

## 🧪 Comment lancer l'application

```bash
# Cloner le dépôt
git clone https://github.com/hels237/gestion-inventaires-produits.git

# Accéder au dossier
cd gestion-inventaires-produits

# Construire le projet
./mvnw clean install

# Lancer le projet
./mvnw spring-boot:run
```

## 📘 Documentation Swagger

### Accéder à la documentation interactive :

```bash
http://localhost:8080/swagger-ui.html
```

# 📬 Endpoints principaux

## 🔹 GET /api/produits
Retourne la liste de tous les produits :

```json
[
  {
    "id": 1,
    "nom": "Smartphone",
    "prix": 499.99,
    "quantiteEnStock": 4
  }
]
```

## 🔹 POST /api/produits
**Créer un nouveau produit :**

```json
{
  "nom": "Tablet Pro",
  "prix": 399.99,
  "quantiteEnStock": 7
}
```
### Réponse : 201 Created

## 🔹 PUT /api/produits/{id}
**Mettre à jour un produit existant :**

```json
{
  "nom": "Tablet Pro Updated",
  "prix": 449.99,
  "quantiteEnStock": 10
}
```
### Réponse : 200 OK

## 🔹 DELETE /api/produits/{id}
Supprime le produit correspondant à l'ID donné.

**Réponse :**  
- 204 No Content  
- ou 404 Not Found si non trouvé

## 🔹 GET /api/produits/stock-bas?seuil=5
Retourne les produits dont la quantité en stock est inférieure au seuil (défaut: 5) :

```json
[
  {
    "id": 2,
    "nom": "Laptop",
    "prix": 899.99,
    "quantiteEnStock": 2
  }
]
```

## 🔹 GET /api/produits/search?nom=smartphone
Recherche des produits par nom :

```json
[
  {
    "id": 1,
    "nom": "Smartphone",
    "prix": 499.99,
    "quantiteEnStock": 4
  }
]
```

### ✅ Validation

- Le champ `nom` est obligatoire et doit être unique
- Le champ `prix` doit être positif (> 0.01)
- Le champ `quantiteEnStock` doit être ≥ 0
ean Validation pour la validation des données