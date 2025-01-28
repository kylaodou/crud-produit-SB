package com.bethel.crudproduitsb.models;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
public class ProduitDto {
    @NotEmpty(message = "Le champ nom est obligateoire !")
    private String nom;

    @Size(min = 20, message = "La description  doit être d'au moins 20 lettres")
    @Size(max = 2000, message = "La description  doit être d'au plus 2000 lettres")
    private String description;

    @NotEmpty(message = "Le champ marque est obligateoire !")
    private String marque;


    @Min(value = 25, message = "Le prix doit être >= 25")
    private double prix;

    @NotEmpty(message = "Le champ categorie est obligateoire !")
    private String categorie;

    private MultipartFile imageFile;
}
