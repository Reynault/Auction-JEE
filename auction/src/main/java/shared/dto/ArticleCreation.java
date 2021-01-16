package shared.dto;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import web.config.customValidator.ValidDate;

public class ArticleCreation implements Serializable {

    @NotNull(message = "Veuillez fournir un nom")
    @Size(min = 1, max = 255, message = "Le nom ne doit pas être vide et doit faire moins de 255 caractères")
    private String name;

    @NotNull(message = "Veuillez fournir une description")
    @Size(min = 1, max = 1000, message = "La description ne doit pas être vide et doit faire moins de 1000 caractères")
    private String description;

    @NotNull(message = "Veuillez fournir un prix de base")
    @DecimalMax("99999999.99")
    @DecimalMin("0.00")
    private double firstPrice;

    @NotNull(message = "Veuillez fournir un tableau de catégories")
    @NotEmpty(message = "Veuillez spécifier au moins une catégorie")
    private List<String> categories;

    @ValidDate(message = "Veuillez fournir une date valide")
    private String timeLimit;

    public ArticleCreation(String nom, String description, double firstPrice, List<String> categories, String timeLimit) {
        this.name = nom;
        this.description = description;
        this.firstPrice = firstPrice;
        this.categories = categories;
        this.timeLimit = timeLimit;
    }

    public ArticleCreation() {
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

    public double getFirstPrice() {
        return firstPrice;
    }

    public void setFirstPrice(double firstPrice) {
        this.firstPrice = firstPrice;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

}
