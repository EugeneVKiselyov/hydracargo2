package ua.com.idltd.hydracargo.bot.telegram.web.repository.telegram_cars;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.bot.telegram.web.entity.telegram_cars.MenuTelegramCars;

import java.util.List;

@Repository
public interface MenuTelegramCarsRepository extends CrudRepository<MenuTelegramCars, Long> {
    @Query(nativeQuery = true, value = "select * from TABLE(PKG_TELEGRAM_VIEW.vTELEGRAM_VEHICLE_MENU(?1,?2))")
    List<MenuTelegramCars> findByTelegramCars(Long user_id, Long role_id);
}
