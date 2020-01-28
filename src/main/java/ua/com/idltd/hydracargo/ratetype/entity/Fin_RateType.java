package ua.com.idltd.hydracargo.ratetype.entity;

import javax.persistence.*;

@Entity
@Table(name = "FIN_RATETYPE")
public class Fin_RateType {

    @Id
    @SequenceGenerator( name = "FIN_RATETYPE_SEQ", sequenceName = "FIN_RATETYPE_SEQ", allocationSize = 1)
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "FIN_RATETYPE_SEQ")
    private long frt_id;

    @Column(name = "FRT_NAME")
    private String frt_name;

    public long getFrt_id() {
        return frt_id;
    }

    public void setFrt_id(long frt_id) {
        this.frt_id = frt_id;
    }

    public String getFrt_name() {
        return frt_name;
    }

    public void setFrt_name(String frt_name) {
        this.frt_name = frt_name;
    }
}
