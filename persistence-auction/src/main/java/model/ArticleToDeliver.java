package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ARTICLE_TO_DELIVER")
public class ArticleToDeliver implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String description;

    public ArticleToDeliver(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public ArticleToDeliver(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public ArticleToDeliver() {
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

}
