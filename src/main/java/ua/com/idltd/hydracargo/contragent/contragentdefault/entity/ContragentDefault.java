package ua.com.idltd.hydracargo.contragent.contragentdefault.entity;

import javax.persistence.*;

@Entity
@Table(name = "CONTRAGENT_DEFAULT")
public class ContragentDefault {

  @Id
  @Column(name = "CNTD_ID", nullable = false)
  @SequenceGenerator(name = "CONTRAGENT_DEFAULT_SEQ", sequenceName = "CONTRAGENT_DEFAULT_SEQ", allocationSize = 1, initialValue = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTRAGENT_DEFAULT_SEQ")
  public Long cntd_id;

  @Column(name = "CNT_ID")
  public Long cnt_id;

  @Column(name = "CNTD_NAME")
  public String cntd_name;

  @Column(name = "CNTD_ADDRESS")
  public String CNTD_ADDRESS;

  @Column(name = "CNTD_ZIP")
  public String cntd_zip;

  @Column(name = "CNTD_COUNTRY_ID")
  public Long cntd_country_id;

  @Column(name = "CNTD_CITY")
  public String cntd_city;

  @Column(name = "CNTD_CONTACT")
  public String cntd_contact;

  @Column(name = "CNTD_FPG_ID")
  public Long cntd_fpg_id;

  @Column(name = "CNTD_FTPM_ID")
  public Long cntd_ftpm_id;

  @Column(name = "CNTD_FIT_ID")
  public Long cntd_fit_id;

  @Column(name = "CNTD_BRAND")
  public Long cntd_brand=0L;

  @Column(name = "CNTD_BOX_LENGHT")
  public Double cntd_box_lenght;

  @Column(name = "CNTD_BOX_WIDTH")
  public Double cntd_box_width;

  @Column(name = "CNTD_BOX_HEIGHT")
  public Double cntd_box_height;

  @Column(name = "CNTD_SERV_ID")
  public Long cntd_serv_id;

  @Column(name = "CNTD_TYPE_ID")
  public Long cntd_type_id;

  @Column(name = "CNTD_EP_SOURCE")
  public Long cntd_ep_source=0L;

  @Column(name = "CNTD_EP_DEST")
  public Long cntd_ep_dest=0L;

  @Column(name = "CNTD_BS_ID")
  public Long cntd_bs_id;

}
