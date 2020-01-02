package ua.com.idltd.hydracargo.bot.telegram.bot.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.bot.telegram.bot.entity.TelegramVehicle;

import java.util.List;

@Repository
public interface TelegramVehicleRepository extends CrudRepository<TelegramVehicle, Long> {

    @Query(nativeQuery = true, value = "select * from TELEGRAMVEHICLE where TU_ID =? and tv_registrationnumber=?")
    TelegramVehicle findByTu_idAndAndTv_registrationnumber(Long tu_id, String tv_registrationnumber);

    @Query(nativeQuery = true, value = "select * from TELEGRAMVEHICLE where tv_registrationnumber=?")
    List<TelegramVehicle> findByTv_registrationnumber(String tv_registrationnumber);

    @Query(nativeQuery = true, value = "select count(tv_id) from TELEGRAMVEHICLE where TU_ID =?")
    Integer countVehicleByUser(Long tu_id);

    @Query(nativeQuery = true, value =  "select tv.* from TELEGRAMVEHICLE tv,\n" +
                                        "                 TELEGRAMUSER tu\n" +
                                        "where \n" +
                                        "     tu.TU_TELEGRAMID=?\n" +
                                        " and tv.tu_id=tu.tu_id\n" +
                                        " and rownum=1")
    TelegramVehicle getOneVehicleByTelegramIDUser(Long telegramid);

    @Query(nativeQuery = true, value =  "select tv.* from TELEGRAMVEHICLE tv,\n" +
            "                 TELEGRAMUSER tu\n" +
            "where \n" +
            "     tu.TU_TELEGRAMID=?\n" +
            " and tv.tu_id=tu.tu_id")
    List<TelegramVehicle> getVehicleByTelegramIDUser(Long telegramid);

    @Query(nativeQuery = true, value =  "select tv.* from TELEGRAMVEHICLE tv where tv.TU_ID=? and rownum=1")
    TelegramVehicle getOneVehicleBytu_id(Long tu_id);
}
