package ua.com.idltd.hydracargo.zammler.z1c.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "LOAD_ZAMMLER")
public class Load_Zammler {

  @Id
  @Column(name = "LZ_ID", nullable = false)
  @SequenceGenerator(name = "LOAD_ZAMMLER_SEQ", sequenceName = "LOAD_ZAMMLER_SEQ", allocationSize = 1, initialValue = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOAD_ZAMMLER_SEQ")
  public Long lz_id;

  @Column(name = "LZ_LOADDATE")
  @Temporal(TemporalType.DATE)
  public Date lz_loaddate;

  @Column(name = "LZ_DATE")
  @Temporal(TemporalType.DATE)
  public Date lz_date;


}
