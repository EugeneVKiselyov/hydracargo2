package ua.com.idltd.hydracargo.bot.telegram.web.repository.telegram_list;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.bot.telegram.web.entity.telegram_line.MenuTelegramLine;


import java.util.List;

@Repository
public interface MenuTelegramLineRepository extends CrudRepository<MenuTelegramLine, Long> {
    @Query(nativeQuery = true, value = "select * from TABLE(PKG_TELEGRAM_VIEW.vTELEGRAM_LINE_MENU(?1,?2))")
    List<MenuTelegramLine> findByTelegramLine(Long user_id, Long role_id);
}
