package ua.com.idltd.hydracargo.contragent.entity;

import javax.persistence.*;

@Entity
@Table(name = "CONTRAGENT")
public class Contragent {

  @Id
  @Column(name = "CNT_ID", nullable = false)
  @SequenceGenerator(name = "CONTRAGENT_SEQ", sequenceName = "CONTRAGENT_SEQ", allocationSize = 1, initialValue = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTRAGENT_SEQ")
  public Long cnt_id;

  @Column(name = "CNT_CODE")
  public String cnt_code;

  @Column(name = "CNT_NAME")
  public String cnt_name;

}
