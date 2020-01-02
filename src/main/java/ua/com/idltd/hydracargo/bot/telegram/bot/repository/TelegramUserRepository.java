package ua.com.idltd.hydracargo.bot.telegram.bot.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.bot.telegram.bot.entity.TelegramUser;

@Repository
public interface TelegramUserRepository extends CrudRepository<TelegramUser, Long> {
    @Query(nativeQuery = true, value = "select * from TELEGRAMUSER where TU_TELEGRAMID = ?1")
    TelegramUser findByTelegramid(Long telegramid);

    @Query(nativeQuery = true, value = "select * from TELEGRAMUSER where TU_ID=?")
    TelegramUser findByTu_id(Long user_id);
}
