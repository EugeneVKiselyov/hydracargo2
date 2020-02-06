package ua.com.idltd.hydracargo.scan.controller;

import java.math.BigDecimal;

public class ScanResult {
    private final BigDecimal result;
    private final String ds_color;
    private final String shipment;

    public ScanResult(BigDecimal result, String ds_color, String shipment) {
        this.result = result;
        this.ds_color = ds_color;
        this.shipment = shipment;
    }

    public BigDecimal getResult() {
        return result;
    }

    public String getDs_color() {
        return ds_color;
    }

    public String getShipment() {
        return shipment;
    }
}
