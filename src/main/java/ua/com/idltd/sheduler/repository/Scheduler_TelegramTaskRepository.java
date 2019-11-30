package ua.com.idltd.sheduler.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.idltd.sheduler.entity.Scheduler_TelegramTask;

import java.util.List;

@Repository
public interface Scheduler_TelegramTaskRepository extends CrudRepository<Scheduler_TelegramTask, Long> {

    // Сообщения на отправку
    @Query(nativeQuery = true, value = "select * from Scheduler_TELEGRAMTASK where tt_state is null")
    List<Scheduler_TelegramTask> queryByStateisNull();

}
