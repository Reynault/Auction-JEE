package shared.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import model.Article;

public class ArticleEntity implements Entity {

    private long id;
    private String name;
    private String description;
    @JsonProperty("caqsdqsdtegories")
    private List<String> categories;

    public ArticleEntity(long id, String name, String description, List<String> categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categories = categories;
    }

    public static ArticleEntity convertArticleToEntity(Article article) {
        return new ArticleEntity(
                article.getId(),
                article.getName(),
                article.getDescription(),
                article.getCategoriesAsStrings()
        );
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
