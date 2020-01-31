package ua.com.idltd.hydracargo.contragent.rateproductgroup.entity;

import javax.persistence.*;

@Entity
@Table(name = "FIN_CONTRAGENTRATEPRODUCTGROUP")
public class ContragentRateProductGroup {

  @Id
  @Column(name = "FCR_ID", nullable = false)
  @SequenceGenerator(name = "FIN_CONTRAGENTRATEPRODUCTGROUP_SEQ", sequenceName = "FIN_CONTRAGENTRATEPRODUCTGROUP_SEQ", allocationSize = 1, initialValue = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FIN_CONTRAGENTRATEPRODUCTGROUP_SEQ")
  public Long fcr_id;

  @Column(name = "CNT_ID")
  public Long cnt_id;

  @Column(name = "FRT_ID")
  public Long frt_id;

  @Column(name = "FPG_ID")
  public Long fpg_id;

  @Column(name = "FCR_PRICE")
  public Double fcr_price;

  @Column(name = "FCR_PRICE_BRAND")
  public Double fcr_price_brand;

}
