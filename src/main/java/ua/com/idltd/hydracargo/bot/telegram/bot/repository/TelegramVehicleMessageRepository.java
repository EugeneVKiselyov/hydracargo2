package ua.com.idltd.hydracargo.bot.telegram.bot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.bot.telegram.bot.entity.TelegramVehicleMessage;

@Repository
public interface TelegramVehicleMessageRepository extends CrudRepository<TelegramVehicleMessage, Long> {

}
