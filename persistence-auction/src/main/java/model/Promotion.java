package model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PROMOTION")
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String description;
    @ManyToMany(targetEntity = Category.class, cascade = {
        CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Parameter> parameters;

    public Promotion(long id, String description, List<Parameter> parameters) {
        this.id = id;
        this.description = description;
        this.parameters = parameters;
    }

    public Promotion(String description, List<Parameter> parameters) {
        this.description = description;
        this.parameters = parameters;
    }

    public Promotion() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

}
