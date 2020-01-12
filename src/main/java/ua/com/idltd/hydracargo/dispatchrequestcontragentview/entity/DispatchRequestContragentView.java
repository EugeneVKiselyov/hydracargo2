package ua.com.idltd.hydracargo.dispatchrequestcontragentview.entity;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import ua.com.idltd.hydracargo.dispatch_status.entity.Dispatch_Status;

import javax.persistence.*;
import java.util.Date;

@Entity
@Immutable
@Subselect(
        "SELECT    " +
                "d.DIS_ID, d.DIS_NUM, d.REQ_ID, " +
                "   r.REQ_SEATSNUM_P, r.REQ_WEIGHT_P, " +
                "   d.DIS_SEATSNUM, d.DIS_AGENT_ARRIVAL_DATE, d.DIS_AIRPORT_ARRIVAL_DATE, " +
                "   d.DIS_TRANSIT_AIRPORT_DATE, d.DIS_DEPARTURE_AIRPORT_DATE_F, d.DIS_ARRIVAL_DATE_KIEV_F, " +
                "   d.DIS_WEIGHT_F, d.DIS_VOLUME_WEIGHT_F, d.DIS_CSS_DATE, " +
                "   d.ROUTE_ID, d.DIS_ARRIVAL_DATE_KIEV_P, d.AWB_ID, a.AWB_NUM, " +
                "   d.EP_ID, d.DST_ID, d.RS_ID, " +
                "   r.REQ_NUM, r.CNT_ID, c.CNT_CODE, c.CNT_NAME,  " +
                "   e.EP_CODE, d.DIS_DOCUMENTDATE, " +
                "   (select count(*) from DECLARATION_CACHE dc where dc.req_id=r.req_id and dc.dis_id=d.dis_id) countshipment " +
                "FROM DISPATCH d, request r, contragent c, entrepot e, awb a  " +
                "where r.req_id=d.req_id   " +
                "and c.CNT_ID = r.CNT_ID   " +
                "and e.EP_ID = d.EP_ID " +
                "and a.AWB_ID (+) = d.AWB_ID "
)
public class DispatchRequestContragentView {

  @Id
  @Column(name = "DIS_ID", nullable = false)
  @SequenceGenerator(name = "DISPATCH_SEQ", sequenceName = "DISPATCH_SEQ", allocationSize = 1, initialValue = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DISPATCH_SEQ")
  public Long dis_id;

  @Column(name = "DIS_NUM")
  public String dis_num;

  @Column(name = "REQ_ID")
  public Long req_id;

  @Column(name = "EP_ID")
  public Long ep_id;

  @Column(name = "EP_CODE")
  public String ep_code;

  @Column(name = "AWB_ID")
  public Long awb_id;

  @Column(name = "AWB_NUM")
  public String awb_num;

  @Column(name = "REQ_SEATSNUM_P")
  public Long req_seatsnum_p;

  @Column(name = "REQ_WEIGHT_P")
  public Double req_weight_p;

  @Column(name = "DIS_SEATSNUM")
  public Long dis_seatsnum;

  @Column(name = "DIS_AGENT_ARRIVAL_DATE")
  @Temporal(TemporalType.DATE)
  public Date dis_agent_arrival_date;

  @Column(name = "DIS_AIRPORT_ARRIVAL_DATE")
  @Temporal(TemporalType.DATE)
  public Date dis_airport_arrival_date;

  @Column(name = "DIS_TRANSIT_AIRPORT_DATE")
  @Temporal(TemporalType.DATE)
  public Date dis_transit_airport_date;

  @Column(name = "DIS_DEPARTURE_AIRPORT_DATE_F")
  @Temporal(TemporalType.DATE)
  public Date dis_departure_airport_date_f;

  @Column(name = "DIS_ARRIVAL_DATE_KIEV_F")
  @Temporal(TemporalType.DATE)
  public Date dis_arrival_date_kiev_f;

  @Column(name = "DIS_WEIGHT_F")
  public Double dis_weight_f;

  @Column(name = "DIS_VOLUME_WEIGHT_F")
  public Double dis_volume_weight_f;

  @Column(name = "DIS_CSS_DATE")
  @Temporal(TemporalType.DATE)
  public Date dis_css_date;

  @Column(name = "ROUTE_ID")
  public Long route_id;

  @Column(name = "DIS_ARRIVAL_DATE_KIEV_P")
  @Temporal(TemporalType.DATE)
  public Date dis_arrival_date_kiev_p;

  @Column(name = "REQ_NUM")
  public String req_num;

  @Column(name = "CNT_ID")
  public Long cnt_id;

  @Column(name = "CNT_CODE")
  public String cnt_code;

  @Column(name = "CNT_NAME")
  public String cnt_name;

   @Column(name = "RS_ID")
   public Long rs_id;

    @Column(name = "BS_ID")
    public Long bs_id;

    @Column(name = "BS_NAME")
    public String bs_name;

    @Column(name = "COUNTSHIPMENT")
    public Long countshipment;

    @Column(name = "DIS_DOCUMENTDATE")
    @Temporal(TemporalType.DATE)
    public Date dis_documentdate;

    @ManyToOne
    @JoinColumn(name = "dst_id", referencedColumnName = "dst_id")
    private Dispatch_Status dispatch_status;

    public Date getDis_documentdate() {
        return dis_documentdate;
    }

    public void setDis_documentdate(Date dis_documentdate) {
        this.dis_documentdate = dis_documentdate;
    }

    public Dispatch_Status getDispatch_status() {
        return dispatch_status;
    }

    public void setDispatch_status(Dispatch_Status dispatch_status) {
        this.dispatch_status = dispatch_status;
    }

    public Long getCountshipment() {
        return countshipment;
    }

    public void setCountshipment(Long countshipment) {
        this.countshipment = countshipment;
    }

    public Long getBs_id() {
        return bs_id;
    }

    public void setBs_id(Long bs_id) {
        this.bs_id = bs_id;
    }

    public String getBs_name() {
        return bs_name;
    }

    public void setBs_name(String bs_name) {
        this.bs_name = bs_name;
    }

    public Long getDis_id() {
        return dis_id;
    }

    public void setDis_id(Long dis_id) {
        this.dis_id = dis_id;
    }

    public String getDis_num() {
        return dis_num;
    }

    public void setDis_num(String dis_num) {
        this.dis_num = dis_num;
    }

    public Long getReq_id() {
        return req_id;
    }

    public void setReq_id(Long req_id) {
        this.req_id = req_id;
    }

    public Long getEp_id() {
        return ep_id;
    }

    public void setEp_id(Long ep_id) {
        this.ep_id = ep_id;
    }

    public Long getAwb_id() {
        return awb_id;
    }

    public void setAwb_id(Long awb_id) {
        this.awb_id = awb_id;
    }

    public Long getDis_seatsnum() {
        return dis_seatsnum;
    }

    public void setDis_seatsnum(Long dis_seatsnum) {
        this.dis_seatsnum = dis_seatsnum;
    }

    public Date getDis_agent_arrival_date() {
        return dis_agent_arrival_date;
    }

    public void setDis_agent_arrival_date(Date dis_agent_arrival_date) {
        this.dis_agent_arrival_date = dis_agent_arrival_date;
    }

    public Date getDis_airport_arrival_date() {
        return dis_airport_arrival_date;
    }

    public void setDis_airport_arrival_date(Date dis_airport_arrival_date) {
        this.dis_airport_arrival_date = dis_airport_arrival_date;
    }

    public Date getDis_transit_airport_date() {
        return dis_transit_airport_date;
    }

    public void setDis_transit_airport_date(Date dis_transit_airport_date) {
        this.dis_transit_airport_date = dis_transit_airport_date;
    }

    public Date getDis_departure_airport_date_f() {
        return dis_departure_airport_date_f;
    }

    public void setDis_departure_airport_date_f(Date dis_departure_airport_date_f) {
        this.dis_departure_airport_date_f = dis_departure_airport_date_f;
    }

    public Date getDis_arrival_date_kiev_f() {
        return dis_arrival_date_kiev_f;
    }

    public void setDis_arrival_date_kiev_f(Date dis_arrival_date_kiev_f) {
        this.dis_arrival_date_kiev_f = dis_arrival_date_kiev_f;
    }

    public Double getDis_weight_f() {
        return dis_weight_f;
    }

    public void setDis_weight_f(Double dis_weight_f) {
        this.dis_weight_f = dis_weight_f;
    }

    public Double getDis_volume_weight_f() {
        return dis_volume_weight_f;
    }

    public void setDis_volume_weight_f(Double dis_volume_weight_f) {
        this.dis_volume_weight_f = dis_volume_weight_f;
    }

    public Date getDis_css_date() {
        return dis_css_date;
    }

    public void setDis_css_date(Date dis_css_date) {
        this.dis_css_date = dis_css_date;
    }

    public Long getRoute_id() {
        return route_id;
    }

    public void setRoute_id(Long route_id) {
        this.route_id = route_id;
    }

    public Date getDis_arrival_date_kiev_p() {
        return dis_arrival_date_kiev_p;
    }

    public void setDis_arrival_date_kiev_p(Date dis_arrival_date_kiev_p) {
        this.dis_arrival_date_kiev_p = dis_arrival_date_kiev_p;
    }

    public Long getCnt_id() {
        return cnt_id;
    }

    public String getReq_num() {
        return req_num;
    }

    public String getCnt_name() {
        return cnt_name;
    }

    public String getCnt_code() {
        return cnt_code;
    }

    public String getEp_code() {
        return ep_code;
    }

    public void setEp_code(String ep_code) {
        this.ep_code = ep_code;
    }

    public String getAwb_num() {
        return awb_num;
    }

    public void setAwb_num(String awb_num) {
        this.awb_num = awb_num;
    }

    public Long getReq_seatsnum_p() {
        return req_seatsnum_p;
    }

    public void setReq_seatsnum_p(Long req_seatsnum_p) {
        this.req_seatsnum_p = req_seatsnum_p;
    }

    public Double getReq_weight_p() {
        return req_weight_p;
    }

    public void setReq_weight_p(Double req_weight_p) {
        this.req_weight_p = req_weight_p;
    }

    public void setReq_num(String req_num) {
        this.req_num = req_num;
    }

    public void setCnt_id(Long cnt_id) {
        this.cnt_id = cnt_id;
    }

    public void setCnt_code(String cnt_code) {
        this.cnt_code = cnt_code;
    }

    public void setCnt_name(String cnt_name) {
        this.cnt_name = cnt_name;
    }

    public Long getRs_id() {
        return rs_id;
    }

    public void setRs_id(Long rs_id) {
        this.rs_id = rs_id;
    }
}
