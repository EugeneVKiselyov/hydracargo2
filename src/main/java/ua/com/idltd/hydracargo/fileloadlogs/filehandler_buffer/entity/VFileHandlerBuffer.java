package ua.com.idltd.hydracargo.fileloadlogs.filehandler_buffer.entity;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.*;
import java.util.Date;

@Entity
@Immutable
@Subselect(
        "select FHLB_ID, FHLB_NAME, FHL_ID, FHLB_DATE, FHLB_USER from FILEHANDLER_BUFFER"
)
public class VFileHandlerBuffer {

  @Id
  @Column(name = "FHLB_ID", nullable = false)
  public Long fhlb_id;

  @Column(name = "FHL_ID")
  public Long fhl_id;

  @Column(name = "FHLB_NAME")
  public String fhlb_name;

  @Column(name = "FHLB_DATE")
  public Date fhlb_date;

  @Column(name = "FHLB_USER")
  public String fhlb_user;
}
