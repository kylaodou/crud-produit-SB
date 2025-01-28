package com.bethel.crudproduitsb.repository;

import com.bethel.crudproduitsb.models.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitsRepository extends JpaRepository<Produit, Integer> {
}
