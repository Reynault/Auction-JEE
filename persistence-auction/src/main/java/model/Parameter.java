package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PARAMETER")
public class Parameter implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int indexParam;
    private double parameterValue;

    public Parameter(long id, int indexParam, double parameterValue) {
        this.id = id;
        this.indexParam = indexParam;
        this.parameterValue = parameterValue;
    }

    public Parameter(int indexParam, double parameterValue) {
        this.indexParam = indexParam;
        this.parameterValue = parameterValue;
    }

    public Parameter() {
    }

    public int getIndex() {
        return indexParam;
    }

    public void setIndex(int indexParam) {
        this.indexParam = indexParam;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(double parameterValue) {
        this.parameterValue = parameterValue;
    }

}
