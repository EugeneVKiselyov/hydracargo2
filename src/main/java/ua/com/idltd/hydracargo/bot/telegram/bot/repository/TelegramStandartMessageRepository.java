package ua.com.idltd.hydracargo.bot.telegram.bot.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.bot.telegram.bot.entity.TelegramStandartMessage;

@Repository
public interface TelegramStandartMessageRepository extends CrudRepository<TelegramStandartMessage, Long> {
    @Query(nativeQuery = true, value = "select tsm_code from TELEGRAMSTANDARTMESSAGE where TSM_TEXT = ?1 and rownum=1")
    Long getCodeByText(String tsm_text);

    @Query(nativeQuery = true, value = "select tsm_text from TELEGRAMSTANDARTMESSAGE where TSM_code = ?1 and tsm_lang=?2")
    String getTextByCode(Long message_code, String lang);
}
