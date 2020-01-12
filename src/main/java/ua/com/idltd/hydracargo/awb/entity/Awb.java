package ua.com.idltd.hydracargo.awb.entity;

import javax.persistence.*;

@Entity
@Table(name = "AWB")
public class Awb {

  @Id
  @Column(name = "AWB_ID", nullable = false)
  @SequenceGenerator(name = "AWB_SEQ", sequenceName = "AWB_SEQ", allocationSize = 1, initialValue = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AWB_SEQ")
  public Long awb_id;

  @Column(name = "AWB_NUM")
  public String awb_num;

  @Column(name = "AWB_DESCRIPTION")
  public String awb_description;

  @Column(name = "AWB_WEIGHT_F")
  public Double awb_weight_f;

  @Column(name = "AWB_VOLUME_WEIGHT")
  public Double awb_volume_weight;

  @Column(name = "AWB_VOLUME_WEIGHT_PAID")
  public Double awb_volume_weight_paid;

  @Column(name = "AWB_FREIGHT_CHARGE")
  public Double awb_freight_charge;

  @Column(name = "AWB_BRAND_SURCHARGE")
  public Double awb_brand_surcharge;

  @Column(name = "AWB_REGISTRATION")
  public Double awb_registration;

  @Column(name = "AWB_CUSTOMS_CLEARANCE")
  public Double awb_customs_clearance;

  @Column(name = "AWB_TRANSPORT_AIRPORT")
  public Double awb_transport_airport;

  @Column(name = "AWB_TRANSPORT_AGENT")
  public Double awb_transport_agent;

  @Column(name = "AWB_PACKING")
  public Double awb_packing;

  @Column(name = "AWB_WEIGHT_CALC")
  public Double awb_weight_calc;

  @Column(name = "AWB_VOLUME_WEIGHT_CALC")
  public Double awb_volume_weight_calc;

  public Double getAwb_weight_calc() {
    return awb_weight_calc;
  }

  public void setAwb_weight_calc(Double awb_weight_calc) {
    this.awb_weight_calc = awb_weight_calc;
  }

  public Double getAwb_volume_weight_calc() {
    return awb_volume_weight_calc;
  }

  public void setAwb_volume_weight_calc(Double awb_volume_weight_calc) {
    this.awb_volume_weight_calc = awb_volume_weight_calc;
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

  public String getAwb_description() {
    return awb_description;
  }

  public void setAwb_description(String awb_description) {
    this.awb_description = awb_description;
  }

  public Double getAwb_weight_f() {
    return awb_weight_f;
  }

  public void setAwb_weight_f(Double awb_weight_f) {
    this.awb_weight_f = awb_weight_f;
  }

  public Double getAwb_volume_weight() {
    return awb_volume_weight;
  }

  public void setAwb_volume_weight(Double awb_volume_weight) {
    this.awb_volume_weight = awb_volume_weight;
  }

  public Double getAwb_volume_weight_paid() {
    return awb_volume_weight_paid;
  }

  public void setAwb_volume_weight_paid(Double awb_volume_weight_paid) {
    this.awb_volume_weight_paid = awb_volume_weight_paid;
  }

  public Double getAwb_freight_charge() {
    return awb_freight_charge;
  }

  public void setAwb_freight_charge(Double awb_freight_charge) {
    this.awb_freight_charge = awb_freight_charge;
  }

  public Double getAwb_brand_surcharge() {
    return awb_brand_surcharge;
  }

  public void setAwb_brand_surcharge(Double awb_brand_surcharge) {
    this.awb_brand_surcharge = awb_brand_surcharge;
  }

  public Double getAwb_registration() {
    return awb_registration;
  }

  public void setAwb_registration(Double awb_registration) {
    this.awb_registration = awb_registration;
  }

  public Double getAwb_customs_clearance() {
    return awb_customs_clearance;
  }

  public void setAwb_customs_clearance(Double awb_customs_clearance) {
    this.awb_customs_clearance = awb_customs_clearance;
  }

  public Double getAwb_transport_airport() {
    return awb_transport_airport;
  }

  public void setAwb_transport_airport(Double awb_transport_airport) {
    this.awb_transport_airport = awb_transport_airport;
  }

  public Double getAwb_transport_agent() {
    return awb_transport_agent;
  }

  public void setAwb_transport_agent(Double awb_transport_agent) {
    this.awb_transport_agent = awb_transport_agent;
  }

  public Double getAwb_packing() {
    return awb_packing;
  }

  public void setAwb_packing(Double awb_packing) {
    this.awb_packing = awb_packing;
  }
}
