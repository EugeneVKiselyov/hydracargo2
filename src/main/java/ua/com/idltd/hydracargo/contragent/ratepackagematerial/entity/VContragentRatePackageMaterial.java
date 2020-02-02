package ua.com.idltd.hydracargo.contragent.ratepackagematerial.entity;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.*;

@Entity
@Immutable
@Subselect(
        "select FCRM_ID, CNT_ID, FRT_ID, FTPM_ID, FTPM_PRICE, FTPM_PRICE_BRAND, FTPM_NAME " +
                "from VFIN_CONTRAGENTRATEPACKAGEMATERIAL"
)
public class VContragentRatePackageMaterial {

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

  @Column(name = "FTPM_NAME")
  public String ftpm_name;
}
