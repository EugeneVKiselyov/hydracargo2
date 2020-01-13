package ua.com.idltd.hydracargo.scan.entity;

import javax.persistence.*;

@Entity
@Table(name = "DECLARATION_SCAN")
public class Declaration_scan {

  @Id
  @Column(name = "DS_ID", nullable = false)
  @SequenceGenerator(name = "DECLARATION_SCAN_SEQ", sequenceName = "DECLARATION_SCAN_SEQ", allocationSize = 1, initialValue = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DECLARATION_SCAN_SEQ")
  public Long ds_id;

  @Column(name = "DIS_ID")
  public Long dis_id;

  @Column(name = "DS_SHIPMENT")
  public String ds_shipment;

  @Column(name = "DS_COLOR")
  public String ds_color;

}
