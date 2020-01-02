package ua.com.idltd.hydracargo.bot.telegram.bot.entity;

import javax.persistence.*;

@Entity
@Table(name = "TELEGRAMVEHICLE")
public class TelegramVehicle {

  @Id
  @Column(name = "TV_ID", nullable = false)
  @SequenceGenerator(name = "TELEGRAMVEHICLE_SEQ", sequenceName = "TELEGRAMVEHICLE_SEQ", allocationSize = 1, initialValue = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TELEGRAMVEHICLE_SEQ")
  public Long tv_id;

  @Column(name = "TU_ID")
  public Long tu_id;

  @Column(name = "TV_REGISTRATIONNUMBER")
  public String tv_registrationnumber;

  @Column(name = "TV_CREATEDATE")
  public String tv_createdate;

  @Column(name = "TV_ISACTIVE")
  public Long tv_isactive;

}
