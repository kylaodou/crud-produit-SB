package com.bethel.crudproduitsb.controller;

import com.bethel.crudproduitsb.models.Produit;
import com.bethel.crudproduitsb.models.ProduitDto;
import com.bethel.crudproduitsb.service.ProduitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/produits")
@RequiredArgsConstructor
public class ProduitsController {
    private final ProduitService produitService;

    @GetMapping({"", "/"})
    public String listeProduits(Model model){
        List<Produit> produits = produitService.listeProduits();
        model.addAttribute("produits", produits);
        return "produits/index";
    }

    @GetMapping({"/create"})
    public String formulaireCreation(Model model){
        ProduitDto produitDto = new ProduitDto();
        model.addAttribute("produitDto", produitDto);
        return "produits/create";
    }

    @PostMapping("/create")
    public String addProduit(@Valid @ModelAttribute ProduitDto produitDto, BindingResult result, RedirectAttributes redirectAttributes){
        if(produitDto.getImageFile().isEmpty()){
            result.addError(new FieldError("produitDto", "imageFileName", "Le champ image est obligateoire"));
        }

        if(result.hasErrors()){
            return "produits/create";
        }

        produitService.saveProduit(produitDto);
        redirectAttributes.addFlashAttribute("successCreation", true);
        return "redirect:/produits";
    }

    @GetMapping("/modifier")
    public String modifierProduitFormulaire(@RequestParam int id, Model model){
        ProduitDto produitDto = produitService.getProduitDtoById(id);
        Produit produit = produitService.getProduitById(id);
        model.addAttribute("produitDto", produitDto);
        model.addAttribute("produit", produit);
        if (produitDto == null){
            return "redirect:/produits";
        }else {
            return "produits/modifier";
        }
    }

    @PostMapping("/modifier")
    public String modifierProduit(@RequestParam int id, Model model, @Valid @ModelAttribute ProduitDto produitDto, BindingResult result, RedirectAttributes redirectAttributes){
        Produit produit = produitService.getProduitById(id);

        if(result.hasErrors()){
            model.addAttribute("produit", produit);
            return "produits/modifier";
        }
        produitService.updateProduit(produitDto, produit);
        redirectAttributes.addFlashAttribute("successModification", true);
        return "redirect:/produits";
    }

    @GetMapping("/supprimer")
    public String supprimerProduit(@RequestParam int id){
        Produit produit = produitService.getProduitById(id);
        produitService.supprimerProduit(produit.getId());
        return "redirect:/produits";
    }
}
