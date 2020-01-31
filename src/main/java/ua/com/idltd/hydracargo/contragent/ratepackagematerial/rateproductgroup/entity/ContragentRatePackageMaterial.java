package ua.com.idltd.hydracargo.contragent.ratepackagematerial.rateproductgroup.entity;

import javax.persistence.*;

@Entity
@Table(name = "FIN_CONTRAGENTRATEPACKAGEMATERIAL")
public class ContragentRatePackageMaterial {

  @Id
  @Column(name = "FCRM_ID", nullable = false)
  @SequenceGenerator(name = "FIN_CONTRAGENTRATEPACKAGEMATERIAL_SEQ", sequenceName = "FIN_CONTRAGENTRATEPACKAGEMATERIAL_SEQ", allocationSize = 1, initialValue = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FIN_CONTRAGENTRATEPACKAGEMATERIAL_SEQ")
  public Long fcrm_id;

  @Column(name = "CNT_ID")
  public Long cnt_id;

  @Column(name = "FRT_ID")
  public Long frt_id;

  @Column(name = "FTPM_ID")
  public Long ftpm_id;

  @Column(name = "FTPM_PRICE")
  public Double ftpm_price;

  @Column(name = "FTPM_PRICE_BRAND")
  public Double ftpm_price_brand;

}
