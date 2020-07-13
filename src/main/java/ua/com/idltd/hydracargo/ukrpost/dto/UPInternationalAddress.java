package ua.com.idltd.hydracargo.ukrpost.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ua.com.idltd.hydracargo.justin.dto.JustinResponseResult;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UPInternationalAddress {

    private String country;
    private String city;
    private String region;
    private String foreignStreetHouseApartment;
    private String postcode;


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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getForeignStreetHouseApartment() {
        return foreignStreetHouseApartment;
    }

    public void setForeignStreetHouseApartment(String foreignStreetHouseApartment) {
        this.foreignStreetHouseApartment = foreignStreetHouseApartment;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
