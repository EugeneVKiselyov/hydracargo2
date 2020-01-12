package ua.com.idltd.hydracargo.utils.filehandler.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "FILEHANDLER_DETAIL_LOG")
public class FilehandlerDetailLog {

  @Id
  @Column(name = "fhld_Id", nullable = false)
  @SequenceGenerator(name = "FILEHANDLER_DETAIL_LOG_SEQ", sequenceName = "FILEHANDLER_DETAIL_LOG_SEQ", allocationSize = 1, initialValue = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FILEHANDLER_DETAIL_LOG_SEQ")
  private Long fhld_Id;
  @Column(name = "fhl_Id")
  private Long fhl_Id;
  @Column(name = "fhld_User")
  private String fhld_User;
  @Column(name = "fhld_Date")
  private Date fhld_Date;
  @Column(name = "fhld_Status")
  private String fhld_Status;
  @Column(name = "fhld_Body")
  private String fhld_Body;

  public Long getFhld_Id() {
    return fhld_Id;
  }

  public void setFhld_Id(Long fhld_Id) {
    this.fhld_Id = fhld_Id;
  }

  public Long getFhl_Id() {
    return fhl_Id;
  }

  public void setFhl_Id(Long fhl_Id) {
    this.fhl_Id = fhl_Id;
  }

  public String getFhld_User() {
    return fhld_User;
  }

  public void setFhld_User(String fhld_User) {
    this.fhld_User = fhld_User;
  }

  public Date getFhld_Date() {
    return fhld_Date;
  }

  public void setFhld_Date(Date fhld_Date) {
    this.fhld_Date = fhld_Date;
  }

  public String getFhld_Status() {
    return fhld_Status;
  }

  public void setFhld_Status(String fhld_Status) {
    this.fhld_Status = fhld_Status;
  }

  public String getFhld_Body() {
    return fhld_Body;
  }

  public void setFhld_Body(String fhld_Body) {
    this.fhld_Body = fhld_Body;
  }
}
