package ua.com.idltd.hydracargo.bot.telegram.bot.entity;

import javax.persistence.*;

@Entity
@Table(name = "TELEGRAMSTANDARTMESSAGE")
public class TelegramStandartMessage {

  @Id
  @Column(name = "TSM_ID", nullable = false)
  @SequenceGenerator(name = "TELEGRAMSTANDARTMESSAGE_SEQ", sequenceName = "TELEGRAMSTANDARTMESSAGE_SEQ", allocationSize = 1, initialValue = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TELEGRAMSTANDARTMESSAGE_SEQ")
  public Long tsm_id;

  @Column(name = "TSM_LANG")
  public String tsm_lang;

  @Column(name = "TSM_TEXT")
  public String tsm_text;

  @Column(name = "TSM_CODE")
  public Long tsm_code;

}
