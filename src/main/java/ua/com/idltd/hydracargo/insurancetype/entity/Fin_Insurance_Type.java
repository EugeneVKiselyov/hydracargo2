package ua.com.idltd.hydracargo.insurancetype.entity;

import javax.persistence.*;

@Entity
@Table(name = "FIN_INSURANCE_TYPE")
public class Fin_Insurance_Type {

    @Id
    @SequenceGenerator( name = "FIN_INSURANCE_TYPE_SEQ", sequenceName = "FIN_INSURANCE_TYPE_SEQ", allocationSize = 1)
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "FIN_INSURANCE_TYPE_SEQ")
    @Column(name = "FIT_ID")
    private long fit_id;

    @Column(name = "FIT_NAME")
    private String fit_name;

    @Column(name = "FIT_PERCENT")
    private Double fit_percent;

    @Column(name = "FIT_DESCRIPTION")
    private String fit_description;

    public long getFit_id() {
        return fit_id;
    }

    public void setFit_id(long fit_id) {
        this.fit_id = fit_id;
    }

    public String getFit_name() {
        return fit_name;
    }

    public void setFit_name(String fit_name) {
        this.fit_name = fit_name;
    }

    public Double getFit_percent() {
        return fit_percent;
    }

    public void setFit_percent(Double fit_percent) {
        this.fit_percent = fit_percent;
    }

    public String getFit_description() {
        return fit_description;
    }

    public void setFit_description(String fit_description) {
        this.fit_description = fit_description;
    }
}
