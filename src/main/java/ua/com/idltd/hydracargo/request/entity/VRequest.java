package ua.com.idltd.hydracargo.request.entity;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.*;
import java.util.Date;

@Entity
@Immutable
@Subselect(
        "select CNT_ID, CNT_NAME, REQ_ID, REQ_NUM, BS_ID, BS_NAME, RS_ID, RS_NAME, REQ_DATE, EP_ID, EP_CODE, EP_ID_DEST, EP_CODE_DEST, " +
                "REQ_WEIGHT_P, REQ_WEIGHT_F, REQ_SEATSNUM_P, REQ_SEATSNUM_F, " +
                "REQ_ARRIVAL_DATE_P, REQ_ARRIVAL_DATE_F, REQ_DEPARTURE_DATE, REQ_EP_DEST_DATE, REQ_CONTRAGENT_DATE, REQ_FEE, REQ_ADDEXPENSES " +
                "from VREQUEST"
)
public class VRequest {

  @Id
  @Column(name = "REQ_ID", nullable = false)
  public Long req_id;
  @Column(name = "CNT_ID")
  public Long cnt_id;
  @Column(name = "CNT_NAME")
  public String cnt_name;
  @Column(name = "REQ_NUM")
  public String req_num;
  @Column(name = "BS_ID")
  public Long bs_id;
  @Column(name = "BS_NAME")
  public String bs_name;
  @Column(name = "RS_ID")
  public Long rs_id;
  @Column(name = "RS_NAME")
  public String rs_name;
  @Column(name = "REQ_DATE")
  @Temporal(TemporalType.DATE)
  public Date req_date;
  @Column(name = "EP_ID")
  public Long ep_id;
  @Column(name = "EP_CODE")
  public String ep_code;
  @Column(name = "EP_ID_DEST")
  public Long ep_id_dest;
  @Column(name = "EP_CODE_DEST")
  public String ep_code_dest;
  @Column(name = "REQ_WEIGHT_P")
  public Double req_weight_p;
  @Column(name = "REQ_WEIGHT_F")
  public Double req_weight_f;
  @Column(name = "REQ_SEATSNUM_P")
  public Long req_seatsnum_p;
  @Column(name = "REQ_SEATSNUM_F")
  public Long req_seatsnum_f;

  @Column(name = "REQ_ARRIVAL_DATE_P")
  @Temporal(TemporalType.DATE)
  public Date req_arrival_date_p;
  @Column(name = "REQ_ARRIVAL_DATE_F")
  @Temporal(TemporalType.DATE)
  public Date req_arrival_date_f;
  @Column(name = "REQ_DEPARTURE_DATE")
  @Temporal(TemporalType.DATE)
  public Date req_departure_date;
  @Column(name = "REQ_EP_DEST_DATE")
  @Temporal(TemporalType.DATE)
  public Date req_ep_dest_date;
  @Column(name = "REQ_CONTRAGENT_DATE")
  @Temporal(TemporalType.DATE)
  public Date req_contragent_date;

  @Column(name = "REQ_FEE")
  public Double req_fee;
  @Column(name = "REQ_ADDEXPENSES")
  public Double req_addexpenses;
}
