package ua.com.idltd.hydracargo.sheduler.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "SHEDULER_EVENT")
public class Sheduler_Event {

  @Id
  @Column(name = "SE_ID", nullable = false)
  @SequenceGenerator(name = "SHEDULER_EVENT_SEQ", sequenceName = "SHEDULER_EVENT_SEQ", allocationSize = 1, initialValue = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SHEDULER_EVENT_SEQ")
  public Long se_id;

  @Column(name = "SET_ID")
  public Long set_id;

  @Column(name = "SE_TODO")
  public String se_todo;

  @Column(name = "SE_CREATE")
  public Date se_create;

  @Column(name = "SE_STATE")
  public String se_state;

  @Column(name = "SE_RESULT_DETAIL")
  public String se_result_detail;

  @Column(name = "SE_DONE")
  public Date se_done;

  @Column(name = "SE_WORKER_ID")
  public Long se_worker_id;
}
