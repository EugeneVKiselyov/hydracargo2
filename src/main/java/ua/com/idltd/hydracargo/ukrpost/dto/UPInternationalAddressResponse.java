package ua.com.idltd.hydracargo.ukrpost.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ua.com.idltd.hydracargo.justin.dto.JustinResponseResult;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UPInternationalAddressResponse {

    private Long id;
    private String postcode;
    private String region;
    private String district;
    private String city;
    private String street;
    private String houseNumber;
    private String apartmentNumber;
    private String description;
    private String countryside;
    private String foreignStreetHouseApartment;
    private String detailedInfo;
    private String created;
    private String lastModified;
    private String country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
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

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountryside() {
        return countryside;
    }

    public void setCountryside(String countryside) {
        this.countryside = countryside;
    }

    public String getForeignStreetHouseApartment() {
        return foreignStreetHouseApartment;
    }

    public void setForeignStreetHouseApartment(String foreignStreetHouseApartment) {
        this.foreignStreetHouseApartment = foreignStreetHouseApartment;
    }

    public String getDetailedInfo() {
        return detailedInfo;
    }

    public void setDetailedInfo(String detailedInfo) {
        this.detailedInfo = detailedInfo;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
