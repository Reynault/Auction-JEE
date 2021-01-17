package shared.dto;

import java.io.Serializable;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class UserParticipate implements Serializable {

    @NotNull(message = "Veuillez fournir un prix")
    @DecimalMax("99999999.99")
    @DecimalMin("0.00")
    private double value;

    public UserParticipate(double value) {
        this.value = value;
    }

    public UserParticipate() {
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
