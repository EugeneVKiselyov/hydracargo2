package ua.com.idltd.hydracargo.request.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "REQUEST")
public class Request {

  @Id
  @Column(name = "REQ_ID", nullable = false)
  @SequenceGenerator(name = "REQUEST_SEQ", sequenceName = "REQUEST_SEQ", allocationSize = 1, initialValue = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REQUEST_SEQ")
  public Long req_id;

  @Column(name = "CNT_ID")
  public Long cnt_id;

  @Column(name = "REQ_NUM")
  public String req_num;

  @Column(name = "REQ_COST")
  public Double req_cost;

  @Column(name = "REQ_WEIGHT_P")
  public Double req_weight_p;

  @Column(name = "REQ_DESCRIPTION")
  public String req_description;

  @Column(name = "REQ_DEPARTURE_DATE")
  @Temporal(TemporalType.DATE)
  public Date req_departure_date;

  @Column(name = "REQ_ARRIVAL_DATE_P")
  @Temporal(TemporalType.DATE)
  public Date req_arrival_date_p;

  @Column(name = "REQ_PROCESSING_DATE")
  @Temporal(TemporalType.DATE)
  public Date req_processing_date;

  @Column(name = "RS_ID")
  public Long rs_id;

  @Column(name = "EP_ID")
  public Long ep_id;

  @Column(name = "REQ_SEATSNUM_P")
  public Long req_seatsnum_p;

  @Column(name = "REQ_ARRIVAL_DATE_F")
  @Temporal(TemporalType.DATE)
  public Date req_arrival_date_f;

  @Column(name = "REQ_WEIGHT_F")
  public Double req_weight_f;

  @Column(name = "REQ_SEATSNUM_F")
  public Long req_seatsnum_f;

  @Column(name = "BS_ID")
  public Long bs_id;

  @Column(name = "REQ_FEE")
  public Double req_fee;

  @Column(name = "REQ_ADDEXPENSES")
  public Double req_addexpenses;

}
