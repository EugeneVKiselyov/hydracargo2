package ua.com.idltd.hydracargo.bot.telegram.web.entity.telegram_cars;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MenuTelegramCars {

  @Id
  @Column(name = "TV_ID", nullable = false)
  public Long tv_id;

  @Column(name = "TV_REGISTRATIONNUMBER")
  public String tv_registrationnumber;
}
