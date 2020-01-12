package ua.com.idltd.hydracargo.dispatch_status.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ua.com.idltd.hydracargo.dispatch.entity.Dispatch;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "DISPATCH_STATUS")
public class Dispatch_Status {
    @Id
    @SequenceGenerator( name = "DISPATCH_STATUS_SEQ", sequenceName = "DISPATCH_STATUS_SEQ", allocationSize = 1, initialValue = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "DISPATCH_STATUS_SEQ")
    public Long dst_id;

    @Column(name = "DST_NAME", nullable = false)
    public String dst_name;

    @JsonIgnore
    @OneToMany(mappedBy = "dispatch_status")
    private Set<Dispatch> dispatch;

    public Set<Dispatch> getDispatch() {
        return dispatch;
    }

    public void setDispatch(Set<Dispatch> dispatch) {
        this.dispatch = dispatch;
    }

    public Long getDst_id() {
        return dst_id;
    }

    public void setDst_id(Long dst_id) {
        this.dst_id = dst_id;
    }

    public String getDst_name() {
        return dst_name;
    }

    public void setDst_name(String dst_name) {
        this.dst_name = dst_name;
    }
}
