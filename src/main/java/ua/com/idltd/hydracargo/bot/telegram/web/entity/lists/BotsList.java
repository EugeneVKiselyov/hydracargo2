package ua.com.idltd.hydracargo.bot.telegram.web.entity.lists;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TELEGRAMBOT")
public class BotsList {
  @Id
  @Column(name = "TB_ID", nullable = false)
  public Long tb_id;

  @Column(name = "TB_NAME")
  public String tb_name;
}
