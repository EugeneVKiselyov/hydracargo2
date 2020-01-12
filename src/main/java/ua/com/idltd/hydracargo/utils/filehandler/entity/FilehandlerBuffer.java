package ua.com.idltd.hydracargo.utils.filehandler.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "FILEHANDLER_BUFFER")
public class FilehandlerBuffer {

  @Id
  @Column(name = "FHLB_ID", nullable = false)
  @SequenceGenerator(name = "FILEHANDLER_BUFFER_SEQ", sequenceName = "FILEHANDLER_BUFFER_SEQ", allocationSize = 1, initialValue = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FILEHANDLER_BUFFER_SEQ")
  private Long fhlb_Id;
  @Column(name = "FHL_ID")
  private Long fhl_Id;
  @Column(name = "FHLB_DATE")
  private Date fhlb_Date;
  @Column(name = "FHLB_USER")
  private String fhlb_User;
  @Column(name = "FHLB_NAME")
  private String fhlb_Name;
  @Column(name = "FHLB_EXTENTION")
  private String fhlb_Extention;
  @Lob
  @Column(name = "FHLB_BODY")
  private byte[] fhlb_Body;

  public Long getFhlb_Id() {
    return fhlb_Id;
  }

  public void setFhlb_Id(Long fhlb_Id) {
    this.fhlb_Id = fhlb_Id;
  }

  public Long getFhl_Id() {
    return fhl_Id;
  }

  public void setFhl_Id(Long fhl_Id) {
    this.fhl_Id = fhl_Id;
  }

  public Date getFhlb_Date() {
    return fhlb_Date;
  }

  public void setFhlb_Date(Date fhlb_Date) {
    this.fhlb_Date = fhlb_Date;
  }

  public String getFhlb_User() {
    return fhlb_User;
  }

  public void setFhlb_User(String fhlb_User) {
    this.fhlb_User = fhlb_User;
  }

  public String getFhlb_Name() {
    return fhlb_Name;
  }

  public void setFhlb_Name(String fhlb_Name) {
    this.fhlb_Name = fhlb_Name;
  }

  public String getFhlb_Extention() {
    return fhlb_Extention;
  }

  public void setFhlb_Extention(String fhlb_Extention) {
    this.fhlb_Extention = fhlb_Extention;
  }

  public byte[] getFhlb_Body() {
    return fhlb_Body;
  }

  public void setFhlb_Body(byte[] fhlb_Body) {
    this.fhlb_Body = fhlb_Body;
  }
}
