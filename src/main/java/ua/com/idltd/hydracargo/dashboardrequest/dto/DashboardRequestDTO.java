package ua.com.idltd.hydracargo.dashboardrequest.dto;

import ua.com.idltd.hydracargo.dispatch_status.entity.Dispatch_Status;

import java.util.Date;

public class DashboardRequestDTO {

    private Long dis_id;
    private String dis_num;
    private Long req_id;
    private Long ep_id;
    private String ep_code;
    private Long awb_id;
    private String awb_num;
    private Long req_seatsnum_p;
    private Double req_weight_p;
    private Long dis_seatsnum;
    private Date dis_agent_arrival_date;
    private Date dis_airport_arrival_date;
    private Date dis_transit_airport_date;
    private Date dis_departure_airport_date_f;
    private Date dis_arrival_date_kiev_f;
    private Double dis_weight_f;
    private Double dis_volume_weight_f;
    private Date dis_css_date;
    private Long route_id;
    private Date dis_arrival_date_kiev_p;
    private String req_num;
    private Long cnt_id;
    private String cnt_code;
    private String cnt_name;
    private Long rs_id;
    private Long bs_id;
    private String bs_name;
    private Dispatch_Status dispatch_status;
    private Long countShipment;
    private Date dis_documentdate;

    public DashboardRequestDTO() {
    }

    public Date getDis_documentdate() {
        return dis_documentdate;
    }

    public void setDis_documentdate(Date dis_documentdate) {
        this.dis_documentdate = dis_documentdate;
    }

    public Long getCountShipment() {
        return countShipment;
    }

    public void setCountShipment(Long countShipment) {
        this.countShipment = countShipment;
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

    public String getEp_code() {
        return ep_code;
    }

    public void setEp_code(String ep_code) {
        this.ep_code = ep_code;
    }

    public Long getAwb_id() {
        return awb_id;
    }

    public void setAwb_id(Long awb_id) {
        this.awb_id = awb_id;
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

    public String getReq_num() {
        return req_num;
    }

    public void setReq_num(String req_num) {
        this.req_num = req_num;
    }

    public Long getCnt_id() {
        return cnt_id;
    }

    public void setCnt_id(Long cnt_id) {
        this.cnt_id = cnt_id;
    }

    public String getCnt_code() {
        return cnt_code;
    }

    public void setCnt_code(String cnt_code) {
        this.cnt_code = cnt_code;
    }

    public String getCnt_name() {
        return cnt_name;
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

    public Dispatch_Status getDispatch_status() {
        return dispatch_status;
    }

    public void setDispatch_status(Dispatch_Status dispatch_status) {
        this.dispatch_status = dispatch_status;
    }
}
