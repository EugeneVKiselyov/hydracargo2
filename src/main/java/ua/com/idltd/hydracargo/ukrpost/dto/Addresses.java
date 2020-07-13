package ua.com.idltd.hydracargo.ukrpost.dto;

public class Addresses {

    private Long addressId;
    private boolean main;

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public boolean isMain() {
        return main;
    }

    public void setMain(boolean main) {
        this.main = main;
    }
}
