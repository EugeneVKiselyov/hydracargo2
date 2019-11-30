package ua.com.idltd.sheduler.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.idltd.sheduler.entity.TelegramBotLog;

@Repository
public interface TelegramBotLogRepository extends CrudRepository<TelegramBotLog, Long> {

    @Query(nativeQuery = true, value = "select tbl_text from TELEGRAMBOTLOG where tbl_id = (select max(tbl_id) from TELEGRAMBOTLOG where tbl_userid=?1 )")
    String getPreviosMessage(Long tbl_userid);
}
