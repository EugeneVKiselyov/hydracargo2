package ua.com.idltd.hydracargo.utils.filehandler.entity;

import javax.persistence.*;

@Entity
@Table(name = "FILEHANDLER_ATOM_LOG")
public class FilehandlerAtomLog {

  @Id
  @Column(name = "FHAL_ID", nullable = false)
  @SequenceGenerator(name = "FILEHANDLER_ATOM_LOG_SEQ", sequenceName = "FILEHANDLER_ATOM_LOG_SEQ", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FILEHANDLER_ATOM_LOG_SEQ")
  private Long fhal_id;
  @Column(name = "fhl_id")
  private Long fhl_id;
  @Column(name = "FHAL_ATOM")
  @Lob
  private String fhal_atom;
  @Column(name = "FHAL_ERROR")
  private String fhal_error;
  @Column(name = "FHAL_STATUS")
  private String fhal_status;

  public Long getFhal_id() {
    return fhal_id;
  }

  public void setFhal_id(Long fhal_id) {
    this.fhal_id = fhal_id;
  }

  public Long getFhl_id() {
    return fhl_id;
  }

  public void setFhl_id(Long fhl_id) {
    this.fhl_id = fhl_id;
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
