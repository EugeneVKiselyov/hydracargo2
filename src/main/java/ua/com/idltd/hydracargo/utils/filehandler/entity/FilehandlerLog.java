package ua.com.idltd.hydracargo.utils.filehandler.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "FILEHANDLER_LOG")
public class FilehandlerLog {

  @Id
  @Column(name = "FHL_ID", nullable = false)
  @SequenceGenerator(name = "FILEHANDLER_LOG_SEQ", sequenceName = "FILEHANDLER_LOG_SEQ", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FILEHANDLER_LOG_SEQ")
  private Long fhl_Id;
  @Column(name = "FHL_USER")
  private String fhl_User;
  @Column(name = "FHL_STARTDATE")
  private Date fhl_StartDate;
  @Column(name = "FHL_ENDDATE")
  private Date fhl_EndDate;
  @Column(name = "FHL_STATUS")
  private String fhl_Status;
  @Column(name = "FHL_BODY")
  private String fhl_Body;
  @Column(name = "FHL_ERRORBODY")
  private String fhl_ErrorBody;

  public Long getFhl_Id() {
    return fhl_Id;
  }

  public void setFhl_Id(Long fhl_Id) {
    this.fhl_Id = fhl_Id;
  }

  public String getFhl_User() {
    return fhl_User;
  }

  public void setFhl_User(String fhl_User) {
    this.fhl_User = fhl_User;
  }

  public Date getFhl_StartDate() {
    return fhl_StartDate;
  }

  public void setFhl_StartDate(Date fhl_StartDate) {
    this.fhl_StartDate = fhl_StartDate;
  }

  public Date getFhl_EndDate() {
    return fhl_EndDate;
  }

  public void setFhl_EndDate(Date fhl_EndDate) {
    this.fhl_EndDate = fhl_EndDate;
  }

  public String getFhl_Status() {
    return fhl_Status;
  }

  public void setFhl_Status(String fhl_Status) {
    this.fhl_Status = fhl_Status;
  }

  public String getFhl_Body() {
    return fhl_Body;
  }

  public void setFhl_Body(String fhl_Body) {
    this.fhl_Body = fhl_Body;
  }

  public String getFhl_ErrorBody() {
    return fhl_ErrorBody;
  }

  public void setFhl_ErrorBody(String fhl_ErrorBody) {
    this.fhl_ErrorBody = fhl_ErrorBody;
  }
}
