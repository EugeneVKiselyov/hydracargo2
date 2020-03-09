package ua.com.idltd.hydracargo.typepackagematerial.ratetype.entity;

import javax.persistence.*;

@Entity
@Table(name = "FIN_TYPEPACKAGEMATERIAL")
public class Fin_TypePackageMaterial {

    @Id
    @SequenceGenerator( name = "FIN_TYPEPACKAGEMATERIAL_SEQ", sequenceName = "FIN_TYPEPACKAGEMATERIAL_SEQ", allocationSize = 1)
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "FIN_TYPEPACKAGEMATERIAL_SEQ")
    private long ftpm_id;

    @Column(name = "FTPM_NAME")
    private String ftpm_name;

    @Column(name = "FTPM_PRICE")
    private Double ftpm_price;

    @Column(name = "FTPM_PRICE_BRAND")
    private Double ftpm_price_brand;

    public long getFtpm_id() {
        return ftpm_id;
    }

    public void setFtpm_id(long ftpm_id) {
        this.ftpm_id = ftpm_id;
    }

    public String getFtpm_name() {
        return ftpm_name;
    }

    public void setFtpm_name(String ftpm_name) {
        this.ftpm_name = ftpm_name;
    }

    public Double getFtpm_price() {
        return ftpm_price;
    }

    public void setFtpm_price(Double ftpm_price) {
        this.ftpm_price = ftpm_price;
    }

    public Double getFtpm_price_brand() {
        return ftpm_price_brand;
    }

    public void setFtpm_price_brand(Double ftpm_price_brand) {
        this.ftpm_price_brand = ftpm_price_brand;
    }
}
