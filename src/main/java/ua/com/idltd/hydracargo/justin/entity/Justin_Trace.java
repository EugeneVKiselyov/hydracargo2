package ua.com.idltd.hydracargo.justin.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "JUSTIN_TRACE")
public class Justin_Trace {

  @Id
  @Column(name = "JT_ID", nullable = false)
  @SequenceGenerator(name = "JUSTIN_TRACE_SEQ", sequenceName = "JUSTIN_TRACE_SEQ", allocationSize = 1, initialValue = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "JUSTIN_TRACE_SEQ")
  public Long jt_id;

  @Column(name = "JT_CREATEDATE")
  @Temporal(TemporalType.DATE)
  public Date jt_createdate;

  @Column(name = "JT_LASTUPDATE")
  @Temporal(TemporalType.DATE)
  public Date jt_lastupdate;

  @Column(name = "JT_SHIPMENT")
  public String jt_shipment;

  @Column(name = "JT_JASTINTTN")
  public String jt_jastinttn;

  @Column(name = "JT_QUERY")
  public String jt_query;

  @Column(name = "JT_RESPONSE")
  public String jt_response;

  @Column(name = "JT_JSONRESPONSE")
  public String jt_jsonresponse;

  @Column(name = "JT_JSONCONVERTERROR")
  public String jt_jsonconverterror;

  @Column(name = "JT_RESPONSEERROR")
  public String jt_responseerror;

  @Column(name = "JT_STATUS")
  public Long jt_status;

}
