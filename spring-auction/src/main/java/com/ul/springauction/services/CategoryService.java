package com.ul.springauction.services;

import com.ul.springauction.DAO.repository.CategoryRepository;
import model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service en charge des catégories
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Crée et ajoute les catégories qui n'existe pas et renvoie les catégories cherchées
    public List<Category> findAndAddCategories(List<String> categoriesName){
        List<Category> categories = new ArrayList<>();
        for(String name : categoriesName){
            Category cat = categoryRepository.findByName(name);
            if (cat == null){
                Category newCat = new Category(name);
                categoryRepository.save(newCat);
                categories.add(newCat);
            } else {
                categories.add(cat);
            }
        }
        return categories;
    }


    // Récupère les catégories associées à une liste de noms de catégorie
    public List<Category> getAllByList(List<String> categories){
        List<Category> categoryList = new ArrayList<>();
        if (categories != null) {
            for (String s : categories) {
                categoryList.add(categoryRepository.findByName(s));
            }
            return categoryList;
        } else {
            return null;
        }
    }
}
