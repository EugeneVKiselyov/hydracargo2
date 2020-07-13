package ua.com.idltd.hydracargo.ukrpost.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UPInternationalSenderClient {

    private String latinName;
    private String email;
    private String type;
    private String phoneNumber;
    private List<Addresses> addresses;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLatinName() {
        return latinName;
    }

    public void setLatinName(String latinName) {
        this.latinName = latinName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Addresses> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Addresses> addresses) {
        this.addresses = addresses;
    }
}
