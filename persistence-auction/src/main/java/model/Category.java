package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "CATEGORY")
@NamedQueries({
    @NamedQuery(
            name = "Category.findOne",
            query = "SELECT c FROM Category c WHERE c.name = :name"
    )
})
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;

    public Category(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category(String name) {
        this.name = name;
    }

    public Category() {
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

}
