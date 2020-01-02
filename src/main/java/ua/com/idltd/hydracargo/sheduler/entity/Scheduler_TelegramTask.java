package ua.com.idltd.hydracargo.sheduler.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "SCHEDULER_TELEGRAMTASK")
public class Scheduler_TelegramTask {

  @Id
  @Column(name = "TT_ID", nullable = false)
  @SequenceGenerator(name = "SCHEDULER_TELEGRAMTASK_SEQ", sequenceName = "SCHEDULER_TELEGRAMTASK_SEQ", allocationSize = 1, initialValue = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SCHEDULER_TELEGRAMTASK_SEQ")
  public Long tt_id;

  @Column(name = "TT_CHATID")
  public Long tt_chatid;

  @Column(name = "TT_TEXT", nullable = false)
  public String tt_text	;

  @Column(name = "TT_CREATED")
  public Date tt_created;

  @Column(name = "TT_DONE")
  public Date tt_done;

  @Column(name = "TT_STATE")
  public String tt_state;

  @Column(name = "TT_ERROR")
  public String tt_error;

  @Column(name = "TT_SOURCETELEGRAMID")
  public Long tt_sourcetelegramid;

}
