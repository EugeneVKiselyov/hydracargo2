package ua.com.idltd.hydracargo.bot.telegram.web.repository.telegram_user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.bot.telegram.web.entity.telegram_user.MenuTelegramUser;

import java.util.List;

@Repository
public interface MenuTelegramUserRepository extends CrudRepository<MenuTelegramUser, Long> {

    @Query(nativeQuery = true, value = "select * from TABLE(PKG_TELEGRAM_VIEW.vTELEGRAM_USERS_MENU(?1,?2))")
    List<MenuTelegramUser> findByTelegramAll(Long user_id, Long role_id);

}
