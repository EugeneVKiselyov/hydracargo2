package ua.com.idltd.hydracargo.bot.telegram.web.repository.telegram_user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.bot.telegram.web.entity.telegram_user.DetailTelegramUser;

import java.util.List;

@Repository
public interface DetailTelegramUserRepository extends CrudRepository<DetailTelegramUser, Long> {
    @Query(nativeQuery = true, value = "select * from TABLE(PKG_TELEGRAM_VIEW.vTELEGRAM_USERS_DETAIL(?1,?2,?3))")
    List<DetailTelegramUser> DetailTelegramByID(Long user_id, Long role_id, Long tu_id);

}
