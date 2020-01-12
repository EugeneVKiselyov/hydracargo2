package ua.com.idltd.hydracargo.type_export.entity;

import javax.persistence.*;

@Entity
@Table(name = "TYPE_EXPORT")
public class Type_export {

    @Id
    @SequenceGenerator( name = "TYPE_EXPORT_SEQ", sequenceName = "TYPE_EXPORT_SEQ", allocationSize = 1, initialValue = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "TYPE_EXPORT_SEQ")
    public Long type_id;

    @Column(name = "TYPE_NAME", nullable = false)
    public String type_name;
}
