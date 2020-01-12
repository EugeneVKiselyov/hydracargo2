package ua.com.idltd.hydracargo.request_status.entity;

import javax.persistence.*;

@Entity
@Table(name = "REQUEST_STATUS")
public class Request_status {

  @Id
  @Column(name = "RS_ID", nullable = false)
  @SequenceGenerator(name = "REQUEST_STATUS_SEQ", sequenceName = "REQUEST_STATUS_SEQ", allocationSize = 1, initialValue = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REQUEST_STATUS_SEQ")
  public Long rs_id;

  @Column(name = "RS_NAME")
  public String rs_name;

  @Column(name = "RS_DESCRIPTION")
  public String rs_description;

}
