package ua.com.idltd.hydracargo.scan.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Declaration_cache")
public class Declaration_cache_scan {

    @Id
    @SequenceGenerator( name = "DECLARATION_CACHE_SEQ", sequenceName = "DECLARATION_CACHE_SEQ", allocationSize = 1, initialValue = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "DECLARATION_CACHE_SEQ")
    @Column(name = "DC_ID", nullable = false)
    public Long dc_id;

    @Column(name = "DIS_ID", nullable = false)
    public Long dis_id;

    @Column(name = "DC_SHIPMENT", nullable = false, updatable = false)
    public String dc_shipment;

    public Long getDc_id() {
        return dc_id;
    }

    public void setDc_id(Long dc_id) {
        this.dc_id = dc_id;
    }

    public Long getDis_id() {
        return dis_id;
    }

    public void setDis_id(Long dis_id) {
        this.dis_id = dis_id;
    }

    public String getDc_shipment() {
        return dc_shipment;
    }

    public void setDc_shipment(String dc_shipment) {
        this.dc_shipment = dc_shipment;
    }
}
