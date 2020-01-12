package ua.com.idltd.hydracargo.service.entity;

import javax.persistence.*;

@Entity
@Table(name = "SERVICE")
public class Service {

    @Id
    @SequenceGenerator( name = "SERVICE_SEQ", sequenceName = "SERVICE_SEQ", allocationSize = 1, initialValue = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "SERVICE_SEQ")
    public Long serv_id;

    @Column(name = "SERV_NAME", nullable = false)
    public String serv_name;
}
