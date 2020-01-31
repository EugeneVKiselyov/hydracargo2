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
}
