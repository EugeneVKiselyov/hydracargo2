package ua.com.idltd.hydracargo.bot.telegram.web.entity.telegram_user;

import javax.persistence.*;

@Entity
public class DetailTelegramUser {

  @Id
  @Column(name = "TU_ID", nullable = false)
  public Long tu_id;

  @Column(name = "TU_BOTNAME")
  public String tu_botname;

  @Column(name = "TU_USERNAME")
  public String tu_username;

  @Column(name = "TU_FIRSTNAME")
  public String tu_firstname;

  @Column(name = "TU_LASTNAME")
  public String tu_lastname;

  @Column(name = "TU_LANGUAGECODE")
  public String tu_languagecode;

  @Column(name = "TU_ISBOT")
  public Long tu_isbot;

  @Column(name = "TU_TELEGRAMID")
  public Long tu_telegramid;

  @Column(name = "TU_STARTDATE")
  public String tu_startdate;

  @Column(name = "TU_ENDDATE")
  public String tu_enddate;

  @Column(name = "TU_ISACTIVE")
  public Long tu_isactive;

  @Column(name = "ROL_ID")
  public Long rol_id;

}
