package ua.com.idltd.hydracargo.request.box.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "BOX")
public class Box {

  @Id
  @Column(name = "BOX_ID", nullable = false)
  @SequenceGenerator(name = "BOX_SEQ", sequenceName = "BOX_SEQ", allocationSize = 1, initialValue = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOX_SEQ")
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

  @Column(name = "BOX_COST")
  public Float box_cost;

}
