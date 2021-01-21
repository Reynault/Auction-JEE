package com.ul.springauction.shared.dto;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class NewParticipation implements DtoObject {

    @NotNull(message = "Veuillez fournir un prix")
    @DecimalMax("99999999.99")
    @DecimalMin("0.00")
    private double value;

    public NewParticipation(){}

    public NewParticipation(double value){
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
