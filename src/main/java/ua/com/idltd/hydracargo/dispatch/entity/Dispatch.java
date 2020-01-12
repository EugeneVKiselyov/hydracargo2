package ua.com.idltd.hydracargo.dispatch.entity;

import ua.com.idltd.hydracargo.dispatch_status.entity.Dispatch_Status;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "DISPATCH")
public class Dispatch {

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

  @Column(name = "AWB_ID")
  public Long awb_id;

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

  @Column(name = "RS_ID")
  public Long rs_id;

  @Column(name = "DIS_DOCUMENTDATE")
  @Temporal(TemporalType.DATE)
  public Date dis_documentdate;

    @ManyToOne
    @JoinColumn(name = "dst_id", referencedColumnName = "dst_id")
    private Dispatch_Status dispatch_status;

    public Dispatch_Status getDispatch_status() {
        return dispatch_status;
    }

    public void setDispatch_status(Dispatch_Status dispatch_status) {
        this.dispatch_status = dispatch_status;
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

    public Long getRs_id() {
        return rs_id;
    }

    public void setRs_id(Long rs_id) {
        this.rs_id = rs_id;
    }

    public Date getDis_documentdate() {
        return dis_documentdate;
    }

    public void setDis_documentdate(Date dis_documentdate) {
        this.dis_documentdate = dis_documentdate;
    }
}
