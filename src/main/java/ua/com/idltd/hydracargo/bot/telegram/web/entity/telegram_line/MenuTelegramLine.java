package ua.com.idltd.hydracargo.bot.telegram.web.entity.telegram_line;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MenuTelegramLine {

  @Id
  @Column(name = "TVM_ID", nullable = false)
  public Long tvm_id;

  @Column(name = "TV_ID")
  public Long tv_id;

  @Column(name = "TV_NUMBER")
  public String tv_number;

  @Column(name = "TU_ID")
  public Long tu_id;

  @Column(name = "TU_NAME")
  public String tu_name;

  @Column(name = "TVM_MESSAGE")
  public String tvm_message;

  @Column(name = "TVMS_ID")
  public Long tvms_id;

  @Column(name = "TVMS_NAME")
  public String tvms_name;

  @Column(name = "TVM_CREATEDATE")
  public String tvm_createdate;

  @Column(name = "TVM_STATUS_CHANGE")
  public String tvm_status_change;

  @Column(name = "TVM_DIRECT")
  public Long tvm_direct;

  @Column(name = "TVM_MAX_COLOR")
  public String tvm_max_color;
}
