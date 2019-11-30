package ua.com.idltd.sheduler.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TELEGRAMBOTLOG")
public class TelegramBotLog {

  @Id
  @Column(name = "TBL_ID", nullable = false)
  @SequenceGenerator(name = "TELEGRAMBOTLOG_SEQ", sequenceName = "TELEGRAMBOTLOG_SEQ", allocationSize = 1, initialValue = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TELEGRAMBOTLOG_SEQ")
  public Long tbl_id;

  @Column(name = "TBL_USERID")
  public Long tbl_userid;

  @Column(name = "TBL_TEXT", nullable = false)
  public String tbl_text	;

  @Column(name = "TBL_USERNAME")
  public String tbl_username;

  @Column(name = "TBL_MESSAGE")
  public String tbl_message;

  @Column(name = "TBL_CREATED")
  public Date tbl_created;

}
