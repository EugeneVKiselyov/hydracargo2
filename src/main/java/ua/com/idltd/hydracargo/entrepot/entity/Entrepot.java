package ua.com.idltd.hydracargo.entrepot.entity;

import javax.persistence.*;

@Entity
@Table(name = "ENTREPOT")
public class Entrepot {

  @Id
  @Column(name = "EP_ID", nullable = false)
  @SequenceGenerator(name = "ENTREPOT_SEQ", sequenceName = "ENTREPOT_SEQ", allocationSize = 1, initialValue = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ENTREPOT_SEQ")
  public Long ep_id;

  @Column(name = "EP_CODE")
  public String ep_code;

  @Column(name = "EP_COUNTRY")
  public String ep_country;

  @Column(name = "EP_DESCRIPTION")
  public String ep_description;

}
