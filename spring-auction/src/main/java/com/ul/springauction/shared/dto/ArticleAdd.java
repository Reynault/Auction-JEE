package com.ul.springauction.shared.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * DTO pour l'ajout d'un article sur le site par un utilisateur
 */
public class ArticleAdd implements DtoObject{

    @NotNull(message = "Veuillez fournir un nom")
    @Size(min = 1, max = 255, message = "Le nom ne doit pas être vide et doit faire moins de 255 caractères")
    private String name;

    @NotNull(message = "Veuillez fournir une description")
    @Size(min = 1, max = 1000, message = "La description ne doit pas être vide et doit faire moins de 1000 caractères")
    private String description;

    @NotNull(message = "Veuillez fournir un tableau de catégories")
    @NotEmpty(message = "Veuillez spécifier au moins une catégorie")
    private List<String> categories;

    public ArticleAdd(){}

    public ArticleAdd(String name, String description, List<String> categories){
        this.name = name;
        this.description = description;
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
