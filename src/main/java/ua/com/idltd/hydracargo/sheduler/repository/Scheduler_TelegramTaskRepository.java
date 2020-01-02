package ua.com.idltd.hydracargo.sheduler.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.sheduler.entity.Scheduler_TelegramTask;

import java.util.List;

@Repository
public interface Scheduler_TelegramTaskRepository extends CrudRepository<Scheduler_TelegramTask, Long> {

    // Сообщения на отправку
    @Query(nativeQuery = true, value = "select * from Scheduler_TELEGRAMTASK where tt_state is null")
    List<Scheduler_TelegramTask> queryByStateisNull();

    // Сообщения на отправку
    @Query(nativeQuery = true, value = "select count(tt_id) from Scheduler_TELEGRAMTASK where tt_state is null")
    Long countByStateisNull();

    // Последнее принятое сообщение
    @Query(nativeQuery = true, value = "select * from Scheduler_TELEGRAMTASK \n" +
            "where \n" +
            "tt_id = (select max(tt_id) from Scheduler_TELEGRAMTASK where tt_state='done' and tt_chatid=?) ")
    Scheduler_TelegramTask getLastDoneMessage(Long tt_chatid);

    // Последнее принятое сообщение от водителя
    @Query(nativeQuery = true, value = "select * from Scheduler_TELEGRAMTASK \n" +
            "where \n" +
            "tt_id = (select max(tt_id) from Scheduler_TELEGRAMTASK tt,telegramuser tu where tt.tt_state='done' and tt.tt_chatid=? and tt.TT_SOURCETELEGRAMID = tu.TU_TELEGRAMID and tu.ROL_ID=4) ")
    Scheduler_TelegramTask getLastDoneMessageFromVehicle(Long tt_chatid);
}
