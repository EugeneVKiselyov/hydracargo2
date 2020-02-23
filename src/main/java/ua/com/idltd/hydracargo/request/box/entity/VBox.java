package ua.com.idltd.hydracargo.request.box.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "VBOX")
public class VBox {

  @Id
  @Column(name = "BOX_ID", nullable = false)
  public Long box_id;

  @Column(name = "REQ_ID")
  public Long req_id;

  @Column(name = "BOX_NUM")
  public String box_num;

  @Column(name = "BOX_WEIGHT_P")
  public Float box_weight_p;

  @Column(name = "BOX_LENGHT_P")
  public Float box_lenght_p;

  @Column(name = "BOX_WIDTH_P")
  public Float box_width_p;

  @Column(name = "BOX_HEIGHT_P")
  public Float box_height_p;

  @Column(name = "BOX_VOLUME_WEIGHT_P")
  public Float box_volume_weight_p;

  @Column(name = "BOX_PACKING_DATE")
  @Temporal(TemporalType.DATE)
  public Date box_packing_date;

  @Column(name = "DIS_ID")
  public Long dis_id;

  @Column(name = "DIS_NUM")
  public String dis_num;

  @Column(name = "BOX_COST")
  public Float box_cost;

  @Column(name = "BOX_BRAND")
  public Long box_brand;

  @Column(name = "BOX_QTY")
  public Long box_qty;

  @Column(name = "BOX_UNITPRICE")
  public Double box_unitprice;

  @Column(name = "FPG_ID")
  public Long fpg_id;

  @Column(name = "FPG_NAME")
  public String fpg_name;

  @Column(name = "FTPM_ID")
  public Long ftpm_id;

  @Column(name = "FTPM_NAME")
  public String ftpm_name;

  @Column(name = "FIT_ID")
  public Long fit_id;

  @Column(name = "FIT_NAME")
  public String fit_name;

  @Column(name = "FIT_PERCENT")
  public Double fit_percent;

  @Column(name = "BOX_FEE")
  public Double box_fee;

  @Column(name = "BOX_DESCRIPTION")
  public String box_description;

  @Column(name = "BOX_INSHIPMENT")
  public String box_inshipment;

  @Column(name = "BOX_SHIPMENT")
  public String box_shipment;

  @Column(name = "BOX_OUTSHIPMENT")
  public String box_outshipment;

  @Column(name = "BOX_CARPLATE")
  public String box_carplate;

}
