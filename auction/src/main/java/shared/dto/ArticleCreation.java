package shared.dto;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ArticleCreation implements Serializable {

    @NotNull(message = "Veuillez fournir un nom")
    @Size(min = 1, max = 255, message = "Le nom ne doit pas �tre vide et doit faire moins de 255 caract�res")
    private String name;

    @NotNull(message = "Veuillez fournir une description")
    @Size(min = 1, max = 1000, message = "La description ne doit pas �tre vide et doit faire moins de 1000 caract�res")
    private String description;

    @NotNull(message = "Veuillez fournir un tableau de cat�gories")
    @NotEmpty(message = "Veuillez sp�cifier au moins une cat�gorie")
    private List<String> categories;

    public ArticleCreation(String nom, String description, List<String> categories) {
        this.name = nom;
        this.description = description;
        this.categories = categories;
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

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

}
