package model;

import enumeration.PromotionType;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PROMOTION")
@NamedQueries({
    @NamedQuery(
            name = "Promotion.getDaily",
            query = "SELECT p FROM Promotion p WHERE p.daily = TRUE"
    ),
    @NamedQuery(
            name = "Promotion.removeDaily",
            query = "UPDATE Promotion p SET p.daily = FALSE WHERE p.daily = TRUE"
    ),
    @NamedQuery(
            name = "Promotion.getAll",
            query = "SELECT p FROM Promotion p"
    ),
    @NamedQuery(
            name = "Promotion.getOrderedParams",
            query = "SELECT param FROM Promotion p JOIN p.parameters param WHERE p.id = :id ORDER BY param.indexParam"
    ),
    @NamedQuery(
            name = "Promotion.verifyDaily",
            query = "SELECT COUNT(p.id) FROM Promotion p WHERE p.id = :id AND p.daily = TRUE"
    )
})
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String description;

    @OneToMany(targetEntity = Parameter.class, cascade = CascadeType.ALL)
    private List<Parameter> parameters;

    private boolean daily;

    @Enumerated(EnumType.ORDINAL)
    private PromotionType type;

    public Promotion(long id, String description, List<Parameter> parameters, boolean daily, PromotionType type) {
        this.id = id;
        this.description = description;
        this.parameters = parameters;
        this.daily = daily;
        this.type = type;
    }

    public Promotion(String description, List<Parameter> parameters, boolean daily, PromotionType type) {
        this.description = description;
        this.parameters = parameters;
        this.daily = daily;
        this.type = type;
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

    public boolean isDaily() {
        return daily;
    }

    public void setDaily(boolean daily) {
        this.daily = daily;
    }

    public PromotionType getType() {
        return type;
    }

    public void setType(PromotionType type) {
        this.type = type;
    }

}
