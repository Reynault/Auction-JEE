package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PARAMETER")
public class Parameter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private double parameterValue;

    public Parameter(long id, String name, double parameterValue) {
        this.id = id;
        this.name = name;
        this.parameterValue = parameterValue;
    }

    public Parameter(String name, double parameterValue) {
        this.name = name;
        this.parameterValue = parameterValue;
    }

    public Parameter() {
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

    public double getValue() {
        return parameterValue;
    }

    public void setValue(double parameterValue) {
        this.parameterValue = parameterValue;
    }

}
