package com.ul.springauction.shared.dto;

import model.Address;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegisterAddress implements DtoObject {

    @NotNull(message = "Veuillez donner votre pays")
    @Size(min = 1, max = 255, message = "La taille du pays doit faire au moins 1 caractere")
    private String country;

    @NotNull(message = "Veuillez donner votre ville")
    @Size(min = 1, max = 255, message = "La taille de la ville doit faire au moins 1 caractère")
    private String city;

    @NotNull(message = "Veuillez donner votre rue")
    @Size(min = 1, max = 255, message = "La taille de la rue doit faire au moins 1 caractere")
    private String street;

    @NotNull(message = "Veuillez donner votre code postal")
    @Size(min = 1, max = 255, message = "La taille du code postal doit faire au moins 1 caractere")
    private String code;

    //default constructor for JSON parsing
    public RegisterAddress(){}

    public RegisterAddress(String country, String city, String street, String code){
        this.country = country;
        this.city = city;
        this.street = street;
        this.code = code;
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

    public static Address convertToAddress(RegisterAddress addr){
        if (addr == null){
            return null;
        } else {
            return new Address(addr.getCountry(), addr.getCity(), addr.getStreet(), addr.getCode());
        }
    }
}
