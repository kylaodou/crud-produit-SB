# CRUD de produits — Spring Boot et Thymeleaf

Application web Spring Boot de gestion d'un catalogue de produits : liste, ajout, modification et
suppression, avec des pages générées par Thymeleaf et une base MySQL.

## Fonctionnalités

- Liste des produits (`/produits`).
- Ajout d'un produit avec validation du formulaire (`/produits/create`).
- Modification (`/produits/modifier?id=...`).
- Suppression (`/produits/supprimer?id=...`).

## Stack technique

- Java 21, Spring Boot 3.3 (Web, Data JPA, Validation, Thymeleaf)
- MySQL (mysql-connector-j)
- Lombok
- Maven

## Prérequis

- JDK 21
- MySQL 8

## Configuration

La base est créée à partir du nom de l'application. Créez-la puis ajustez les identifiants dans
`src/main/resources/application.properties` :

```sql
CREATE DATABASE `crud-produit-SB`;
```

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/crud-produit-SB
spring.datasource.username=root
spring.datasource.password=
```

Les tables sont créées automatiquement (`spring.jpa.hibernate.ddl-auto=update`).

## Lancement

```bash
git clone https://github.com/kylaodou/crud-produit-SB.git
cd crud-produit-SB
./mvnw spring-boot:run
```

Ouvrez http://localhost:8080/produits.

## Auteur

[@kylaodou](https://github.com/kylaodou)
