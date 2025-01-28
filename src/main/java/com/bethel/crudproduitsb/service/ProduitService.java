package com.bethel.crudproduitsb.service;

import com.bethel.crudproduitsb.models.Produit;
import com.bethel.crudproduitsb.models.ProduitDto;
import com.bethel.crudproduitsb.repository.ProduitsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProduitService {
    private final ProduitsRepository produitsRepository;

    public List<Produit> listeProduits() {
        return produitsRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public void saveProduit(ProduitDto produitDto) {
        MultipartFile image = produitDto.getImageFile();
        Date creationDate = new Date();
        String storageFileName = null;
        if(!produitDto.getImageFile().isEmpty()){
            storageFileName = imageNamePath(image).getFileName().toString();
        }

        Produit newProduit = new Produit();
        newProduit.setCategorie(produitDto.getCategorie());
        newProduit.setCreationDate(creationDate);
        newProduit.setDescription(produitDto.getDescription());
        newProduit.setImageFileName(storageFileName);
        newProduit.setMarque(produitDto.getMarque());
        newProduit.setModificationDate(creationDate);
        newProduit.setNom(produitDto.getNom());
        newProduit.setPrix(produitDto.getPrix());

        produitsRepository.save(newProduit);
    }

    private Path imageNamePath(MultipartFile file) {
        try {
            String uploadDir = "public/img/";
            Path uploadDirPath = Paths.get(uploadDir);

            if(!Files.exists(uploadDirPath)) {
                Files.createDirectories(uploadDirPath);
            }

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            return uploadDirPath.resolve(fileName);

        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement  " + e);
        }
    }

    private void saveImage(MultipartFile file) {
        try {
            Path filePath = imageNamePath(file);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement  " + e);
        }
    }

    public ProduitDto getProduitDtoById(Integer id) {
        try {
            Produit produit = getProduitById(id);
            ProduitDto produitDto = new ProduitDto();
            produitDto.setCategorie(produit.getCategorie());
            produitDto.setDescription(produit.getDescription());
            produitDto.setMarque(produit.getMarque());
            produitDto.setNom(produit.getNom());
            produitDto.setPrix(produit.getPrix());
            produitDto.setImageFile(produitDto.getImageFile());
            return produitDto;

        } catch (Exception e) {
            System.out.println("Exception : " + e);
            return null;
        }
    }

    public Produit getProduitById(Integer id) {
        return produitsRepository.findById(id).orElse(null);
    }

    private void deleteUploadImage(MultipartFile file) {
        try {
            Path oldImagePath = imageNamePath(file);
            Files.delete(oldImagePath);
            saveImage(file);
        } catch (Exception e) {
            System.out.println("Exception : " + e);
        }
    }

    private void deleteImage(String imageName) {
        try {
            Path oldImagePath = Paths.get("public/img/" + imageName);
            Files.delete(oldImagePath);
        } catch (Exception e) {
            System.out.println("Exception : " + e);
        }
    }

    public void updateProduit(ProduitDto produitDto, Produit produit) {
        if(!produitDto.getImageFile().isEmpty()) {
            deleteUploadImage(produitDto.getImageFile());
            saveImage(produitDto.getImageFile());

            String imageName = imageNamePath(produitDto.getImageFile()).getFileName().toString();
            produit.setImageFileName(imageName);
        }
        Date modificationDate = new Date();
        produit.setModificationDate(modificationDate);
            produit.setCategorie(produitDto.getCategorie());
            produit.setDescription(produitDto.getDescription());
            produit.setMarque(produitDto.getMarque());
            produit.setNom(produitDto.getNom());
            produit.setPrix(produitDto.getPrix());
            produitsRepository.save(produit);

    }

    public void supprimerProduit(Integer id) {
        Produit produit = produitsRepository.findById(id).orElse(null);
        assert produit != null;
        deleteImage(produit.getImageFileName());
        produitsRepository.delete(produit);
    }
}


