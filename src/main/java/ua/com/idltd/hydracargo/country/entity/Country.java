package ua.com.idltd.hydracargo.country.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "COUNTRY")
public class Country {

    @Id
    public Long id;

    @Column(name = "COUNTRY_NAMEEN", nullable = false)
    public String country_nameen;

    @Column(name = "COUNTRY_NAMEUA", nullable = false)
    public String country_nameua;

    @Column(name = "COUNTRY_ISO2", nullable = false)
    public String country_iso2;

    @Column(name = "CODE", nullable = false)
    public String code;
}
