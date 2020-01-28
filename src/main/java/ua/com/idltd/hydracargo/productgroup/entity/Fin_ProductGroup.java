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
}
