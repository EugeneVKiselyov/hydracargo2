package ua.com.idltd.hydracargo.business.entity;

import javax.persistence.*;

@Entity
@Table(name = "BUSINESS")
public class Business {

  @Id
  @Column(name = "BS_ID", nullable = false)
  @SequenceGenerator(name = "BUSINESS_SEQ", sequenceName = "BUSINESS_SEQ", allocationSize = 1, initialValue = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BUSINESS_SEQ")
  public Long bs_id;

  @Column(name = "BS_NAME")
  public String bs_name;

}
