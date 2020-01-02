package ua.com.idltd.hydracargo.user.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.user.entity.Workplace;

import java.util.List;

@Repository
public interface WorkplaceRepository extends CrudRepository<Workplace, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM WORKPLACE wp,USERS us, USER_WORKPLACE uwp where us.user_username=?1 and uwp.user_id=us.user_id and wp.wp_id=uwp.WP_ID order by wp.wp_id")
    List<Workplace> queryByUsername(String username);

}
