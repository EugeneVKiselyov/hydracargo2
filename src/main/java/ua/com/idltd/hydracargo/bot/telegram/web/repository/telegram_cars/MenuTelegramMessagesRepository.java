package ua.com.idltd.hydracargo.bot.telegram.web.repository.telegram_cars;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.bot.telegram.web.entity.telegram_cars.MenuTelegramMessages;

import java.util.List;

@Repository
public interface MenuTelegramMessagesRepository extends CrudRepository<MenuTelegramMessages, Long> {
    @Query(nativeQuery = true, value = "select * from TABLE(PKG_TELEGRAM_VIEW.vTELEGRAM_VEHICLE_MES_MENU(?1,?2,?3))")
    List<MenuTelegramMessages> findByTelegramMessages(Long user_id, Long role_id, Long tv_id);
}
