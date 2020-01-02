package ua.com.idltd.hydracargo.bot.telegram.web.repository.lists;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.bot.telegram.web.entity.lists.BotsList;

import java.util.List;

@Repository
public interface BotsListRepository extends CrudRepository<BotsList, Long> {
    @Query(nativeQuery = true, value = "select * from TELEGRAMBOT")
    List<BotsList> getAllBotsList();
}
