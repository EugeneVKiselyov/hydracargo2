package ua.com.idltd.hydracargo.ukrpost.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UPParcel {

    private Double weight;
    private Double length;
    private Double width;
    private Double height;
    private Double declaredPrice;

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getDeclaredPrice() {
        return declaredPrice;
    }

    public void setDeclaredPrice(Double declaredPrice) {
        this.declaredPrice = declaredPrice;
    }
}
