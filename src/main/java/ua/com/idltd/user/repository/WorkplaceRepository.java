package ua.com.idltd.user.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.idltd.user.entity.Workplace;

import java.util.List;

@Repository
public interface WorkplaceRepository extends CrudRepository<Workplace, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM WORKPLACE where loc_id=?1 order by wp_id")
    List<Workplace> queryByLoc_id(Long loc_id);
    @Query(nativeQuery = true, value = "SELECT wp.* FROM WORKPLACE wp, USER_WORKPLACE uwp where uwp.user_id=?1 and wp.loc_id=?2 and wp.WP_ID=uwp.WP_ID order by wp.wp_id")
    List<Workplace> queryByUser_idandLoc_id(Long user_id, Long loc_id);

    @Query(nativeQuery = true, value = "SELECT wp.* FROM WORKPLACE wp, USERS_LOCATIONS ul where ul.user_id=?1 and ul.loc_id=?2 and wp.loc_id=ul.loc_id order by wp.wp_id")
    List<Workplace> AllWorkplaceByUser_idandLoc_id(Long user_id, Long loc_id);
    @Query(nativeQuery = true, value = "SELECT wp.* FROM WORKPLACE wp, USERS_LOCATIONS ul where ul.user_id=?1 and ul.loc_id=?2 and wp.loc_id=ul.loc_id and wp.wp_id not in (select uw.wp_id from USER_WORKPLACE uw where uw.user_id=ul.USER_ID) order by wp.wp_id")
    List<Workplace> AllWorkplaceWithoutUserHaveByUser_idandLoc_id(Long user_id, Long loc_id);

    @Query(nativeQuery = true, value = "SELECT * FROM WORKPLACE wp,USERS us, USER_WORKPLACE uwp where us.username=?1 and uwp.user_id=us.id and wp.wp_id=uwp.WP_ID order by wp.wp_id")
    List<Workplace> queryByUsername(String username);

    @Query(nativeQuery = true, value = "SELECT * FROM WORKPLACE wp WHERE WP_ID not in (SELECT WP_ID FROM USER_WORKPLACE WHERE USER_ID = ?1)")
    List<Workplace> queryNewWorkPlaceByUserID(Long user_id);

    @Query(nativeQuery = true, value = "SELECT * FROM WORKPLACE wp WHERE WP_ID in (SELECT WP_ID FROM USER_WORKPLACE WHERE USER_ID = ?1)")
    List<Workplace> queryWorkPlaceByUserID(Long user_id);
}
