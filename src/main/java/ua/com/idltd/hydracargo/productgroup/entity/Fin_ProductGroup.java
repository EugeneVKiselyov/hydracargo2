package ua.com.idltd.hydracargo.productgroup.entity;

import javax.persistence.*;

@Entity
@Table(name = "FIN_PRODUCTGROUP")
public class Fin_ProductGroup {

    @Id
    @SequenceGenerator( name = "FIN_PRODUCTGROUP_SEQ", sequenceName = "FIN_PRODUCTGROUP_SEQ", allocationSize = 1)
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "FIN_PRODUCTGROUP_SEQ")
    private long fpg_id;

    @Column(name = "FPG_NAME")
    private String fpg_name;

    @Column(name = "FPG_PRICE")
    private Double fpg_price;

    @Column(name = "FPG_PRICE_BRAND")
    private Double fpg_price_brand;

    public long getFpg_id() {
        return fpg_id;
    }

    public void setFpg_id(long fpg_id) {
        this.fpg_id = fpg_id;
    }

    public String getFpg_name() {
        return fpg_name;
    }

    public void setFpg_name(String fpg_name) {
        this.fpg_name = fpg_name;
    }

    public Double getFpg_price() {
        return fpg_price;
    }

    public void setFpg_price(Double fpg_price) {
        this.fpg_price = fpg_price;
    }

    public Double getFpg_price_brand() {
        return fpg_price_brand;
    }

    public void setFpg_price_brand(Double fpg_price_brand) {
        this.fpg_price_brand = fpg_price_brand;
    }
}
