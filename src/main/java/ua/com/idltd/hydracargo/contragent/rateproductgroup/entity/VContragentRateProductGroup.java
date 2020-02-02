package ua.com.idltd.hydracargo.contragent.rateproductgroup.entity;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.*;

@Entity
@Immutable
@Subselect(
        "select FCR_ID,.CNT_ID,FRT_ID,FPG_ID,FCR_PRICE,FCR_PRICE_BRAND,FPG_NAME " +
                "from VFIN_CONTRAGENTRATEPRODUCTGROUP fcrpg"
)
public class VContragentRateProductGroup {

  @Id
  @Column(name = "FCR_ID", nullable = false)
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

  @Column(name = "FPG_NAME")
  public String fpg_name;
}
