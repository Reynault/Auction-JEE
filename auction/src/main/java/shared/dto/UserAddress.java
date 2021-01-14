package shared.dto;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import model.Address;

public class UserAddress implements Serializable {

    @NotNull(message = "Veuillez fournir un pays")
    @Size(min = 1, max = 255, message = "Le pays ne doit pas être vide et doit faire moins de 255 caractères")
    private String country;
    @NotNull(message = "Veuillez fournir une ville")
    @Size(min = 1, max = 255, message = "La ville ne doit pas être vide et doit faire moins de 255 caractères")
    private String city;
    @NotNull(message = "Veuillez fournir une rue")
    @Size(min = 1, max = 255, message = "La rue ne doit pas être vide et doit faire moins de 255 caractères")
    private String street;
    @NotNull(message = "Veuillez fournir un code postal")
    @Size(min = 1, max = 255, message = "Le code postal ne doit pas être vide et doit faire moins de 255 caractères")
    private String code;

    public UserAddress(String country, String city, String street, String code) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.code = code;
    }

    public UserAddress() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static Address convertToAddress(UserAddress address) {
        if (address == null) {
            return null;
        } else {
            return new Address(address.country, address.city, address.street, address.code);
        }
    }
}
