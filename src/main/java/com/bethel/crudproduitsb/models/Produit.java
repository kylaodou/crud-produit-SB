package com.bethel.crudproduitsb.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Data
@Entity
@Table(name = "produits")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;
    private String description;
    private String marque;
    private double prix;

    @Column(columnDefinition = "TEXT")
    private String categorie;
    private Date creationDate;
    private Date modificationDate;
    private String imageFileName;

}
