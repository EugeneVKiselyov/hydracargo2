package ua.com.idltd.hydracargo.ukrpost.entity;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Date;

@Entity
@Table(name = "UKRPOST_TRANSFER")
public class Ukrpost_transfer {

  @Id
  @Column(name = "UT_ID", nullable = false)
  @SequenceGenerator(name = "UKRPOST_TRANSFER_SEQ", sequenceName = "UKRPOST_TRANSFER_SEQ", allocationSize = 1, initialValue = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UKRPOST_TRANSFER_SEQ")
  public Long ut_id;

  @Column(name = "UT_SCOUNTRY")
  public String ut_scountry;

  @Column(name = "UT_SCITY")
  public String ut_scity;

  @Column(name = "UT_SREGION")
  public String ut_sregion;

  @Column(name = "UT_SFOREIGNSTREETHOUSEAPARTMENT")
  public String ut_sforeignstreethouseapartment;

  @Column(name = "UT_SPOSTCODE")
  public String ut_spostcode;

  @Column(name = "UT_SADDERSSID")
  public Long ut_sadderssid;

  @Column(name = "DC_ID")
  public Long DC_ID;

  @Column(name = "UT_STATUS")
  public Long ut_status;

  @Column(name = "UT_RCOUNTRY")
  public String ut_rcountry;

  @Column(name = "UT_RCITY")
  public String ut_rcity;

  @Column(name = "UT_RREGION")
  public String ut_rregion;

  @Column(name = "UT_RDISTRICT")
  public String ut_rdistrict;

  @Column(name = "UT_RSTREET")
  public String ut_rstreet;

  @Column(name = "UT_RHOUSENUMBER")
  public String ut_rhousenumber;

  @Column(name = "UT_RAPARTMENTNUMBER")
  public String ut_rapartmentnumber;

  @Column(name = "UT_RPOSTCODE")
  public String ut_rpostcode;

  @Column(name = "UT_RADDERSSID")
  public Long ut_radderssid;

  @Column(name = "UT_SNAME")
  public String ut_sname;

  @Column(name = "UT_SUNIQUEREGISTRATIONNUMBER")
  public String ut_suniqueregistrationnumber;

  @Column(name = "UT_SPHONENUMBER")
  public String ut_sphonenumber;

  @Column(name = "UT_SBANKCODE")
  public String ut_sbankcode;

  @Column(name = "UT_SBANKACCOUNT")
  public String ut_sbankaccount;

  @Column(name = "UT_SRESIDENT")
  public Long ut_sresident;

  @Column(name = "UT_SEDRPOU")
  public String ut_sedrpou;

  @Column(name = "UT_SEMAIL")
  public String ut_semail;

  @Column(name = "UT_STIN")
  public String ut_stin;

  @Column(name = "UT_SUUID")
  public String ut_suuid;

  @Column(name = "UT_RFIRSTNAME")
  public String ut_rfirstname;

  @Column(name = "UT_RMIDDLENAME")
  public String ut_rmiddlename;

  @Column(name = "UT_RLASTNAME")
  public String ut_rlastname;

  @Column(name = "UT_RPHONENUMBER")
  public String ut_rphonenumber;

  @Column(name = "UT_RTYPE")
  public String ut_rtype;

  @Column(name = "UT_RRESIDENT")
  public String ut_rresident;

  @Column(name = "UT_RUUID")
  public String ut_ruuid;

  @Column(name = "UT_WEIGHT")
  public Double ut_weight;

  @Column(name = "UT_LENGTH")
  public Double ut_length;

  @Column(name = "UT_WIDTH")
  public Double ut_width;

  @Column(name = "UT_HEIGHT")
  public Double ut_height;

  @Column(name = "UT_DECLAREDPRICE")
  public Double ut_declaredprice;

  @Column(name = "UT_PAIDBYRECIPIENT")
  public Long ut_paidbyrecipient;

  @Column(name = "UT_DESCRIPTION")
  public String ut_description;

  @Column(name = "UT_PUUID")
  public String ut_puuid;

  @Column(name = "UT_PTYPE")
  public String ut_ptype;

  @Column(name = "UT_PDELIVERYTYPE")
  public String ut_pdeliverytype;

  @Column(name = "UT_ERROR")
  public String ut_error;

  @Column(name = "UT_SADDRESSRESPONSE")
  public String ut_saddressresponse;

  @Column(name = "UT_RADDRESSRESPONSE")
  public String ut_raddressresponse;

  @Column(name = "UT_SCLIENTRESPONSE")
  public String ut_sclientresponse;

  @Column(name = "UT_RCLIENTRESPONSE")
  public String ut_rclientresponse;

  @Column(name = "UT_SHIPMENTRESPONSE")
  public String ut_shipmentresponse;

  @Column(name = "UT_SADDRESS")
  public String ut_saddress;

  @Column(name = "UT_RADDRESS")
  public String ut_raddress;

  @Column(name = "UT_SCLIENT")
  public String ut_sclient;

  @Column(name = "UT_RCLIENT")
  public String ut_rclient;

  @Column(name = "UT_STYPE")
  public String ut_stype;

  @Column(name = "UT_SHIPMENT")
  public String ut_shipment;

  @Lob
  @Column(name = "UT_LABEL")
  public Blob ut_label;

  @Column(name = "UT_ONFAILRECEIVETYPE")
  public String ut_onfailreceivetype;

}
