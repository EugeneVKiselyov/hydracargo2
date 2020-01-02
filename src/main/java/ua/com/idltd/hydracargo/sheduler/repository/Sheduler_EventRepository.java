package ua.com.idltd.hydracargo.sheduler.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.sheduler.entity.Sheduler_Event;

import java.util.List;

@Repository
public interface Sheduler_EventRepository extends CrudRepository<Sheduler_Event, Long> {

    // Письма на отправку
    @Query(nativeQuery = true, value = "select * from SHEDULER_EVENT where se_state is null")
    List<Sheduler_Event> queryByStateisNull();

}
