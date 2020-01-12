package ua.com.idltd.hydracargo.utils.filehandler.entity;

import javax.persistence.*;

@Entity
@Table(name = "FILEHANDLER_ATOM_LOG")
public class FilehandlerAtomLog {

  @Id
  @Column(name = "FHAL_ID", nullable = false)
  @SequenceGenerator(name = "FILEHANDLER_ATOM_LOG_SEQ", sequenceName = "FILEHANDLER_ATOM_LOG_SEQ", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FILEHANDLER_ATOM_LOG_SEQ")
  private Long fhal_Id;
  @Column(name = "FHL_ID")
  private Long FHL_ID;
  @Column(name = "FHAL_ATOM")
  @Lob
  private String fhal_atom;
  @Column(name = "FHAL_ERROR")
  private String fhal_error;
  @Column(name = "FHAL_STATUS")
  private String fhal_status;

  public Long getFhal_Id() {
    return fhal_Id;
  }

  public void setFhal_Id(Long fhal_Id) {
    this.fhal_Id = fhal_Id;
  }

  public Long getFHL_ID() {
    return FHL_ID;
  }

  public void setFHL_ID(Long FHL_ID) {
    this.FHL_ID = FHL_ID;
  }

  public String getFhal_atom() {
    return fhal_atom;
  }

  public void setFhal_atom(String fhal_atom) {
    this.fhal_atom = fhal_atom;
  }

  public String getFhal_error() {
    return fhal_error;
  }

  public void setFhal_error(String fhal_error) {
    this.fhal_error = fhal_error;
  }

  public String getFhal_status() {
    return fhal_status;
  }

  public void setFhal_status(String fhal_status) {
    this.fhal_status = fhal_status;
  }
}
