package ua.com.idltd.hydracargo.bot.telegram.bot.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TELEGRAMVEHICLEMESSAGE")
public class TelegramVehicleMessage {

  @Id
  @Column(name = "TVM_ID", nullable = false)
  @SequenceGenerator(name = "TELEGRAMVEHICLEMESSAGE_SEQ", sequenceName = "TELEGRAMVEHICLEMESSAGE_SEQ", allocationSize = 1, initialValue = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TELEGRAMVEHICLEMESSAGE_SEQ")
  public Long tvm_id;

  @Column(name = "TU_ID")
  public Long tu_id;

  @Column(name = "TV_ID")
  public Long tv_id;

  @Column(name = "TVM_CREATEDATE")
  public Date tvm_createdate;

  @Column(name = "TVM_MESSAGE")
  public String tvm_message;

  @Column(name = "TVM_DIRECT")
  public Long tvm_direct;

  @Column(name = "TVM_LOCATION")
  public Long tvm_location;
}
